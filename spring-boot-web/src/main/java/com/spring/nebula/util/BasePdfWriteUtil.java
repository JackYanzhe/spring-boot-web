package com.spring.nebula.util;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.spring.nebula.qrcode.vo.ParamItext;

public class BasePdfWriteUtil {

	/**
     * 建表格(以列的数量建)
     * @param colNumber
     * @return
     */
    public static PdfPTable createTable(int colNumber){
        PdfPTable table = new PdfPTable(colNumber);
        try{
            //table.setTotalWidth(maxWidth);
            //table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
            table.setSpacingBefore(10);
            table.setWidthPercentage(100);
        }catch(Exception e){
            e.printStackTrace();
        }
        return table;
    }
 
    /**
     * 建表格(以列的宽度比建)
     * @param widths
     * @return
     */
    public static PdfPTable createTable(float[] widths){
        PdfPTable table = new PdfPTable(widths);
        try{
            //table.setTotalWidth(maxWidth);
            //table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
            table.setSpacingBefore(10);
            table.setWidthPercentage(100);
        }catch(Exception e){
            e.printStackTrace();
        }
        return table;
    }
 
 
    /**
     * 表格中单元格
     * @param value
     * @param font
     * @param align
     * @return
     */
    public static PdfPCell createCell(String value,Font font,int align){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value,font));
        return cell;
    }
 
    /**
     * 表格中单元格
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @param rowspan
     * @return
     */
    public static PdfPCell createCell(String value,Font font,int align_v,int align_h,int colspan,int rowspan){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(align_v);
        cell.setHorizontalAlignment(align_h);
        cell.setColspan(colspan);
        cell.setRowspan(rowspan);
        //cell.setBackgroundColor(backgroundColor);
        cell.setPhrase(new Phrase(value,font));
        return cell;
    }
    
    /**
     * 设置带参数单元格信息
     * @param value
     * @param font
     * @param align_v
     * @param align_h
     * @param colspan
     * @param rowspan
     * @param paramItext
     * @return
     */
    public static PdfPCell createParamCell(String value,Font font,int align_v,int align_h,int colspan,int rowspan,ParamItext paramItext){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(align_v);
        cell.setHorizontalAlignment(align_h);
        cell.setColspan(colspan);
        cell.setRowspan(rowspan);
        cell.setPhrase(new Phrase(value,font));
        //设置背景颜色
        BaseColor baseColor = paramItext.getBaseColor();
        if (null!=baseColor) {
        	cell.setBackgroundColor(baseColor);
		}
        //设置边框
        Integer borderWidth = paramItext.getBorderWidth();
        if (null!=borderWidth) {
			cell.setBorderWidth(borderWidth);
			
		}
        Integer borderWidthBottom = paramItext.getBorderWidthBottom();
        if (null!=borderWidthBottom) {
			cell.setBorderWidthBottom(borderWidthBottom);
		}
        Integer borderWidthLeft = paramItext.getBorderWidthLeft();
        if (null!=borderWidthLeft) {
			cell.setBorderWidthLeft(borderWidthLeft);
		}
        Integer borderWidthRight = paramItext.getBorderWidthRight();
        if (null!=borderWidthRight) {
			cell.setBorderWidthRight(borderWidthRight);
		}
        Integer borderWidthTop = paramItext.getBorderWidthTop();
        if (null!=borderWidthTop) {
			cell.setBorderWidthTop(borderWidthTop);
		}
        
        return cell;
    }
    
    /**
     * 建短语
     * @param value
     * @param font
     * @return
     */
    public static Phrase createPhrase(String value,Font font){
        Phrase phrase = new Phrase();
        phrase.add(value);
        phrase.setFont(font);
        return phrase;
    }
 
    /**
     * 建段落
     * @param value
     * @param font
     * @param align
     * @return
     */
    public static Paragraph createParagraph(String value,Font font,int align){
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Phrase(value,font));
        paragraph.setAlignment(align);
        return paragraph;
    }
    
    /**
     * 建段落
     * @param value
     * @param font
     * @param align
     * @return
     */
    public static Paragraph createColorParagraph(String value,Font font,int align,BaseColor baseParagraphColor){
        Paragraph paragraph = new Paragraph();
        Chunk chunk = new Chunk(value, font);
        if (null!=baseParagraphColor) {
        	chunk.setBackground(baseParagraphColor);
		}
        
        paragraph.add(new Phrase(chunk));
        paragraph.setAlignment(align);
        return paragraph;
    }
    
    /**
     * 宋体
     * @param style
     * @param size
     */
     public static Font getSIMSUN(int style, float size) {
         Font font = new Font();
         InputStream simsunFontFile = BasePdfWriteUtil.class.getResourceAsStream("/fonts/SIMSUN.TTC");
         
         return font;
     }
     
     /**
      * 功能描述:
      *
      * @param inputStream 输入流
      * @return byte[] 数组
      * @author xiaobu
      * @date 2019/3/28 16:03
      * @version 1.0
      */
     public static byte[] inputStream2byte(InputStream inputStream) throws IOException {
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         byte[] buff = new byte[100];
         int rc = 0;
         while ((rc = inputStream.read(buff, 0, 100)) > 0) {
             byteArrayOutputStream.write(buff, 0, rc);
         }
         return byteArrayOutputStream.toByteArray();
     }
     
     public static byte[] inputStreamToArray(InputStream is) throws IOException {
         byte b[] = new byte[8192];
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         while (true) {
             int read = is.read(b);
             if (read < 1)
                 break;
             out.write(b, 0, read);
         }
         out.close();
         return out.toByteArray();
     }
     
     public static void main(String[] args) {
    	 InputStream simsunFontFile = BasePdfWriteUtil.class.getResourceAsStream("/staticfile/font/MSYH.TTF");
    	 System.out.println(simsunFontFile);
    	 try {
         	    byte[] inputStream2byte = BasePdfWriteUtil.inputStreamToArray(simsunFontFile);
         	    System.out.println(inputStream2byte.length);
         	    BaseFont bfChinese_H = BaseFont.createFont("MSYH.ttf", BaseFont.MACROMAN, BaseFont.NOT_EMBEDDED, true, inputStream2byte, null);
        	  
         	   //BaseFont bfChinese_H = BaseFont.createFont("D:\\111\\font\\MSYH.TTF",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
         	  
         	   //BaseFont bfChinese_H = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
         	   System.out.println(bfChinese_H);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
	}
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
         
}
