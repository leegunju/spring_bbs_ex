package com.myweb.www.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class PagingVO {

	private int pageNo; // 현재 화면에 출력된 페이지네이션 번호
	private int qty; //한페이지당 보여지는 게시글 수 (10개)
	
	private String type;
	private String keyword;
	
	public PagingVO() {
		this(1, 10);
	}
	public PagingVO(int pageNo, int qty) {
		this.pageNo = pageNo;
		this.qty = qty;
	}
	public int getPageStart() { //DB상 limit의 시작값을 구하는 메서드
		//limit의 시작값은 0부터 10개  limit 0, 10
		return (this.pageNo-1)*qty;
	}
	//여러가지 타입을 같이 검색하기 위해서 타입을 배열로 구분
	public String[] getTypeToArray() {
		return this.type == null ? new String[] {} : this.type.split("");
	}
}
