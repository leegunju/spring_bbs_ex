package com.myweb.www.service;

import java.util.List;

import com.myweb.www.domain.BoardDTO;
import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.domain.UserVO;

public interface BoardService {

	int register(BoardVO bvo);

	List<BoardVO> getList();

	BoardVO getDetail(int bno);

	int readCount(int bno);

	int modify(BoardVO bvo, UserVO user);

	int remove(int bno, UserVO user);

	List<BoardVO> getList(PagingVO pvo);

	int getTotalCount(PagingVO pvo);
	
	//bvo, fList 묶어서 처리
	int register(BoardDTO bdto);

	BoardDTO getDetailFile(int bno);

	int removeFile(String uuid);

	int modifyFile(BoardDTO bdto, UserVO user);

}
