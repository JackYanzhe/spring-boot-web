package com.spring.nebula.qrcode.vo;

import com.itextpdf.text.BaseColor;

/**
 * 该类用于设置生成pdf样式大小基本参数信息
 * @author zhe.yan
 *
 */
public class ParamItext {

	//设置上下边框大小
	private Integer borderWidth;
	private Integer borderWidthLeft;
	private Integer borderWidthRight;
	private Integer borderWidthTop;
	private Integer borderWidthBottom;
	
	//设置背景颜色
	private BaseColor baseColor;
    
	public ParamItext(int i) {
		super();
		this.borderWidth = i;
	}
	
	public ParamItext(Integer borderWidthLeft, Integer borderWidthRight, Integer borderWidthTop, Integer borderWidthBottom) {
		super();
		this.borderWidthLeft = borderWidthLeft;
		this.borderWidthRight = borderWidthRight;
		this.borderWidthTop = borderWidthTop;
		this.borderWidthBottom = borderWidthBottom;
	}
	
	public ParamItext(BaseColor baseColor) {
		super();
		this.baseColor = baseColor;
	}

	public Integer getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(Integer borderWidth) {
		this.borderWidth = borderWidth;
	}

	public Integer getBorderWidthLeft() {
		return borderWidthLeft;
	}

	public void setBorderWidthLeft(Integer borderWidthLeft) {
		this.borderWidthLeft = borderWidthLeft;
	}

	public Integer getBorderWidthRight() {
		return borderWidthRight;
	}

	public void setBorderWidthRight(Integer borderWidthRight) {
		this.borderWidthRight = borderWidthRight;
	}

	public Integer getBorderWidthTop() {
		return borderWidthTop;
	}

	public void setBorderWidthTop(Integer borderWidthTop) {
		this.borderWidthTop = borderWidthTop;
	}

	public Integer getBorderWidthBottom() {
		return borderWidthBottom;
	}

	public void setBorderWidthBottom(Integer borderWidthBottom) {
		this.borderWidthBottom = borderWidthBottom;
	}

	public BaseColor getBaseColor() {
		return baseColor;
	}

	public void setBaseColor(BaseColor baseColor) {
		this.baseColor = baseColor;
	}

	

	
	
	

	
	
}
