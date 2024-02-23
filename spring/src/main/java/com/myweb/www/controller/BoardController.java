package com.myweb.www.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myweb.www.Handler.FileHandler;
import com.myweb.www.Handler.PagingHandler;
import com.myweb.www.domain.BoardDTO;
import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.FileVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.domain.UserVO;
import com.myweb.www.repository.UserDAO;
import com.myweb.www.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/board/*")
@Slf4j
@Controller
public class BoardController {

	@Inject
	private BoardService bsv;
	@Inject
	private UserDAO udao;
	@Inject
	private FileHandler fhd;
	
	@GetMapping("/register")
	public String registerGet() {
		return "/board/register";
	}
	//required(필수여부)=false :해당 파라미터가 없더라도 예외가 발생하지 않음.
	@PostMapping("/register")
	public String registerPost(BoardVO bvo ,RedirectAttributes rAttr,
			@RequestParam(name="files", required = false)MultipartFile[] files) {
		log.info(">>> bvo "+bvo);
		log.info(">>> files "+files);
		log.info(">>> files[0].getsize "+files[0].getSize());
//		log.info(title+"/"+writer+"/"+content);
//		BoardVO bvo = new BoardVO();
//		bvo.setTitle(title);
//		bvo.setContent(content);
//		bvo.setWriter(writer);
		List<FileVO> fList = null;
		//file 처리 handler로 처리
		if(files[0].getSize()>0) { //데이터가 있다 라는 것을 의미
			//파일 배열을 경로설정, fvo set 다 해서 리스트로 리턴
			fList = fhd.uploadFiles(files); 
		}else {
			log.info("file null");
		}
		//파일과 보드 처리를 별도로 할것인지 같이 (묶어 처리=> 일반적)
		BoardDTO bdto = new BoardDTO(bvo,fList);
		int isOk = bsv.register(bdto);
		
		//int isOk = bsv.register(bvo);
		log.info(">> board register >>"+ (isOk >0 ? "OK" : "FAIL"));
		rAttr.addFlashAttribute("isOk", isOk);
		return "redirect:/board/list";
	}
	
	//insert, update, delete => redirect 처리
	//RedirectAttributes 객체사용 : 데이터의 새로고침
	
	@GetMapping("/list")
	public String list(Model m, PagingVO pvo) {
		log.info(">>>>pageVO : " + pvo);
		List<BoardVO> list = bsv.getList(pvo);
		m.addAttribute("list", list);
		int totalCount = bsv.getTotalCount(pvo);
		log.info(">>>>totalCount : " + totalCount);
		PagingHandler ph = new PagingHandler(pvo, totalCount);
		m.addAttribute("ph", ph);
		return "/board/list";
	}
	
	//detail을 가져와야 하는 케이스 : detail, modify
	@GetMapping({"/detail","/modify"})
	public void detail(Model m, @RequestParam("bno")int bno, HttpServletRequest r) {
		log.info(">>>bno "+bno);
		log.info(">> mapping "+r.getRequestURI());
		String mapping = r.getRequestURI();
		//BoardVO bvo = bsv.getDetail(bno);
		BoardDTO bdto = bsv.getDetailFile(bno);
		String path = mapping.substring(mapping.lastIndexOf("/")+1);
		log.info(">>>path "+path);
		if(path.equals("detail")) {
			int isOk = bsv.readCount(bno);
		}
		m.addAttribute("boardDTO", bdto);
		//m.addAttribute("board", bdto.getBvo());
		//m.addAttribute("flist", bdto.getFlist());
	}
	
	@PostMapping("/modify")
	public String update(RedirectAttributes rAttr, BoardVO bvo, HttpServletRequest request,
			@RequestParam(name="files", required = false)MultipartFile[] files) {
		log.info(">>> bvo "+bvo.toString());
		log.info(">>> files"+ files.toString());
		//세션의 로그인 id가 작성자와 일치하면 삭제 아니면 스크립트에서 작성자가 일치하지 않습니다.
		HttpSession ses = request.getSession();
		UserVO sesUser = (UserVO)ses.getAttribute("ses");  //세션 객체(현재 로그인한 객체)
		//log.info(">>> sesUser "+sesUser.toString());
		UserVO user = udao.getUser(sesUser.getId());
		//UserVO user = udao.getUser(bvo.getWriter());
		
		List<FileVO> flist = null;
		if(files[0].getSize()>0) {
			flist = fhd.uploadFiles(files);
		}
		BoardDTO bdto = new BoardDTO(bvo, flist);
		//DB상 update 하기
		//int isOk = bsv.modify(bvo, user);
		int isOk = bsv.modifyFile(bdto, user);
		log.info(">>> isOk "+(isOk>0 ? "OK" : "FAIL"));
		rAttr.addFlashAttribute("msg_modify", isOk>0 ? "1":"0");
		return "redirect:/board/list";
	}
	
	@GetMapping("/delete")
	public String delete(RedirectAttributes rAttr, @RequestParam("bno")int bno, HttpServletRequest request) {
		//DB상 update하기 isDel = "Y"  => 삭제한글 처리
		HttpSession ses = request.getSession();
		UserVO sesUser = (UserVO)ses.getAttribute("ses");  //세션 객체(현재 로그인한 객체)
		//log.info(">>> sesUser "+sesUser.toString());
		UserVO user = udao.getUser(sesUser.getId());
		
		int isOk = bsv.remove(bno, user);
		log.info(">>> isOk "+(isOk>0 ? "OK" : "FAIL"));
		return "redirect:/board/list";
	}
	
	@DeleteMapping(value="/file/{uuid}", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> removeFile(@PathVariable("uuid")String uuid){
		log.info(">>>> uuid : "+uuid);
		return bsv.removeFile(uuid) > 0 ?
				new ResponseEntity<String>("1", HttpStatus.OK)
				: new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
