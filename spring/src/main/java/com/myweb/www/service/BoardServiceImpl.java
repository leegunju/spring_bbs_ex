package com.myweb.www.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.myweb.www.domain.BoardDTO;
import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.FileVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.domain.UserVO;
import com.myweb.www.repository.BoardDAO;
import com.myweb.www.repository.FileDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

	@Inject
	private BoardDAO bdao;
	@Inject
	private FileDAO fdao;

	@Override
	public int register(BoardVO bvo) {
		log.info("board reigster in");
		return bdao.insert(bvo);
	}

	@Override
	public List<BoardVO> getList() {
		log.info("board list in");
		return bdao.getList();
	}

	@Override
	public BoardVO getDetail(int bno) {
		log.info("board detail in");
		return bdao.getDetail(bno);
	}

	@Override
	public int readCount(int bno) {
		// TODO Auto-generated method stub
		return bdao.readCount(bno);
	}

	@Override
	public int modify(BoardVO bvo, UserVO user) {
		log.info("board modify in");
		BoardVO tmpBoard = bdao.getDetail(bvo.getBno()); //해당 글의 게시글 호출
		if(user ==null || !user.getId().equals(tmpBoard.getWriter())){
			return 0;
		}
		return bdao.updateBoard(bvo);
	}

	@Override
	public int remove(int bno, UserVO user) {
		log.info("board remove in");
		BoardVO tmpBoard = bdao.getDetail(bno); //해당 글의 게시글 호출
		if(user ==null || !user.getId().equals(tmpBoard.getWriter())){
			return 0;
		}
		return bdao.deleteBoard(bno);
	}

	@Override
	public List<BoardVO> getList(PagingVO pvo) {
		log.info("board PagingList in");
		return bdao.selectBoardListPaging(pvo);
	}

	@Override
	public int getTotalCount(PagingVO pvo) {
		// TODO Auto-generated method stub
		return bdao.getTotalCount(pvo);
	}
	
	// file 처리 라인---------------

	@Override
	public int register(BoardDTO bdto) {
		log.info("bvo+fList register in");
		//기존 게시글에 대한 내용 DB 저장 내용 호출
		int isOk = bdao.insert(bdto.getBvo());
		if(bdto.getFlist() == null) { //파일이 없다를 의미
			isOk*=1; //그냥 성공한걸로 치고~
		}else {
			//bvo가 DB에 들어가고, 파일 개수가 있다면...
			if(isOk>0 && bdto.getFlist().size()>0) {
				//register는 등록시 bno가 결정되어있지 않음.
				//int bno = bdto.getBvo().getBno(); //update시는 가능.
				int bno = bdao.selectBno(); //방금 저장된 bvo의 bno 리턴받기
				//fList의 모든 file의 bno를 방금받은 bno로 set
				for(FileVO fvo : bdto.getFlist()) {
					fvo.setBno(bno);
					log.info(">>>> insert File :"+ fvo.toString());
					isOk *= fdao.insertFile(fvo);
				}
			}
		}
		return isOk;
	}

	@Override
	public BoardDTO getDetailFile(int bno) {
		log.info(">>>> detail File in");
		BoardDTO bdto = new BoardDTO();
		bdto.setBvo(bdao.getDetail(bno));  //bvo 호출
		bdto.setFlist(fdao.getFileList(bno)); //fList 호출
		return bdto;
	}

	@Override
	public int removeFile(String uuid) {
		log.info(">>> file remove in");
		return fdao.deleteFile(uuid);
	}

	@Override
	public int modifyFile(BoardDTO bdto, UserVO user) {
		log.info("board modifyFile in");
		BoardVO tmpBoard = bdao.getDetail(bdto.getBvo().getBno()); //해당 글의 게시글 호출
		if(user ==null || !user.getId().equals(tmpBoard.getWriter())){
			return 0;
		}
		int isOk = bdao.updateBoard(bdto.getBvo()); //기존 보드내용 update
		if(bdto.getFlist() == null) {
			isOk *= 1;
		}else {
			if(isOk > 0 && bdto.getFlist().size()>0) {
				int bno = bdto.getBvo().getBno();
				//fList의 모든 file의 bno를 방금받은 bno로 set
				for(FileVO fvo : bdto.getFlist()) {
					fvo.setBno(bno);
					log.info(">>>> insert File :"+ fvo.toString());
					isOk *= fdao.insertFile(fvo); //추가한 파일 추가
					// 삭제는 별도로.
				}
			}
		}
		return isOk;
	}

	
}
