package com.myweb.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BoardVO {

	private int bno;
	private String title;
	private String content;
	private String writer;
	private String reg_date;
	private int read_count;
	private String isDel;
	
}
