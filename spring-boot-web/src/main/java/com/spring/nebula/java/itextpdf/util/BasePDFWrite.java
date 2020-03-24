package com.spring.nebula.java.itextpdf.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.spring.nebula.java.itextpdf.vo.BillOrderItemTem;
import com.spring.nebula.java.itextpdf.vo.BillOrderTem;
import com.spring.nebula.java.itextpdf.vo.ParamItext;


public class BasePDFWrite {
	 
    Document document = null;// 建立一个Document对象
    //头部
    private static Font headFont ;
    //主题内容字体
    private static Font contentFont ;
    //左侧title字体
    private static Font contentTitleFont;
    
    int maxWidth = 520;
    
    public BasePDFWrite() {
		super();
	}

	static{
        BaseFont bfChinese_H;
        try {
            /**
             * 新建一个字体,iText的方法 STSongStd-Light 是字体，在iTextAsian.jar 中以property为后缀
             * UniGB-UCS2-H 是编码，在iTextAsian.jar 中以cmap为后缀 H 代表文字版式是 横版， 相应的 V 代表竖版
             */
            //bfChinese_H = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            bfChinese_H = BaseFont.createFont("D:\\111\\font\\MSYH.TTF",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //bfChinese_H = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
            headFont = new Font(bfChinese_H, 24, Font.BOLD);
            //设置字体颜色
            headFont.setColor(new BaseColor(0,175,80));
            contentTitleFont = new Font(bfChinese_H, 10, Font.BOLD);
            contentFont = new Font(bfChinese_H, 10, Font.NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 设置页面属性
     * @param file
     */
    public BasePDFWrite(File file) {
 
        //自定义纸张 长：40cm,宽：20
        Rectangle rectPageSize = new Rectangle(560, 850);
        // 定义A4页面大小
        //Rectangle rectPageSize = new Rectangle(PageSize.A4);
        rectPageSize = rectPageSize.rotate();// 加上这句可以实现页面的横置
        document = new Document(rectPageSize,80, 80, 10, 20);
        try {
            PdfWriter.getInstance(document,new FileOutputStream(file));
            document.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 设置页面属性
     * @param file
     */
    public BasePDFWrite(OutputStream out) {
 
        //自定义纸张 长：40cm,宽：20
        Rectangle rectPageSize = new Rectangle(560, 850);
        // 定义A4页面大小
        //Rectangle rectPageSize = new Rectangle(PageSize.A4);
        rectPageSize = rectPageSize.rotate();// 加上这句可以实现页面的横置
        document = new Document(rectPageSize,80, 80, 10, 20);
        try {
            PdfWriter.getInstance(document,out);
            document.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 建表格(以列的数量建)
     * @param colNumber
     * @return
     */
    public PdfPTable createTable(int colNumber){
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
    public PdfPTable createTable(float[] widths){
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
    public PdfPCell createCell(String value,Font font,int align){
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
    public PdfPCell createCell(String value,Font font,int align_v,int align_h,int colspan,int rowspan){
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
    public PdfPCell createParamCell(String value,Font font,int align_v,int align_h,int colspan,int rowspan,ParamItext paramItext){
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
    public Phrase createPhrase(String value,Font font){
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
    public Paragraph createParagraph(String value,Font font,int align){
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
    public Paragraph createColorParagraph(String value,Font font,int align,BaseColor baseParagraphColor){
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
     * @param billOrderTem
     * @throws Exception
     */
    public void obtainBillOrderPdf(BillOrderTem billOrderTem) throws Exception{
    	List<BillOrderItemTem> billOrderItemList = billOrderTem.getBillOrderItemTems();
    	
        //设置背景色    INVOICE:0,175,80;INVOICE背景色：249,190,143;Quantity背景色：215,227,187;￡6.91背景色：196,188,150
    	BaseColor headColor = new BaseColor(249,190,143);
    	BaseColor colColor1 = new BaseColor(215,227,187);
    	BaseColor colColor2 = new BaseColor(196,188,150);
    	//加入一行空格
    	Paragraph blankRow = new Paragraph(11f, " ");
        document.add(blankRow);
        //页头信息并设置背景颜色
        //表格信息(按照宽度比)
        float[] widths = {14f,13f,18f,14f,34f,8f};
        PdfPTable table = createTable(widths);
        ParamItext paramItext = new ParamItext(0);
        //设置单元格边框信息及北京色
        ParamItext paramHeadItext = new ParamItext(0, 0, 0, 1);
        paramHeadItext.setBaseColor(headColor);
        table.addCell(createParamCell("INVOICE", headFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,6,2,paramHeadItext));
        table.addCell(createCell("VAT Reg Number", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell(billOrderTem.getvATRegNumber(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,16));
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,16));
        
        table.addCell(createCell("Invoice No.", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(createCell(billOrderTem.getInvoiceNo(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("Invoice Date", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(createCell(billOrderTem.getInvoiceDate(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        
        table.addCell(createCell("Bill To", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(createCell(billOrderTem.getBillTo(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("Address", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,4));
        
        table.addCell(createParamCell(billOrderTem.getAddress_1(), contentFont, Element.ALIGN_LEFT, Element.ALIGN_LEFT,1,2,paramItext));
        table.addCell(createParamCell(billOrderTem.getAddress_2(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1,paramItext));
        table.addCell(createParamCell(billOrderTem.getAddress_3(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1,paramItext));

        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("Phone", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(createCell(billOrderTem.getPhone(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("E-Mail", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(createCell(billOrderTem.geteMail(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        ParamItext paramItext2 = new ParamItext(colColor2);
        ParamItext paramItext3 = new ParamItext(colColor1);
        table.addCell(createParamCell("Invoice Subtotal", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1,paramItext2));
        table.addCell(createCell(billOrderTem.getInvoiceSubtotal(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(createCell("VAT Rate", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell(billOrderTem.getvATRate(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        
        
        
        table.addCell(createParamCell("Invoice Total", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1,paramItext2));
        table.addCell(createCell(billOrderTem.getInvoiceTotal(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        
        table.addCell(createParamCell("Description", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1,paramItext3));
        table.addCell(createParamCell("Quantity", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1,paramItext3));
        table.addCell(createParamCell("Unit Price", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1,paramItext3));
        table.addCell(createParamCell("Amount", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1,paramItext3));
        
        //此处写item描述相关信息
        if (null!=billOrderItemList) {
        	for (BillOrderItemTem billOrderItemTem : billOrderItemList) {
        		table.addCell(createCell(billOrderItemTem.getDescription(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1));
                table.addCell(createCell(billOrderItemTem.getQuantity(), contentFont, Element.ALIGN_BOTTOM, Element.ALIGN_CENTER,1,1));
                table.addCell(createCell(billOrderItemTem.getUnitPrice(), contentFont, Element.ALIGN_BOTTOM, Element.ALIGN_RIGHT,1,1));
                table.addCell(createCell(billOrderItemTem.getAmount(), contentFont, Element.ALIGN_BOTTOM, Element.ALIGN_RIGHT,1,1));
        		
        		
    		}
		}
        //留出一行空值，方便显示好看
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1));
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1));
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell("Postage", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(createParamCell(billOrderTem.getPostage(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1,paramItext2));
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1));
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell("Subtotal", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(createParamCell(billOrderTem.getSubtotal(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1,paramItext2));
        
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1));
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell("VAT", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(createParamCell(billOrderTem.getVat(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1,paramItext2));
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1));
        table.addCell(createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell("Total", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(createParamCell(billOrderTem.getTotal(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1,paramItext2));
        document.add(table);
        document.newPage();
        document.close();
    }
 
    public static void main(String[] args) throws Exception {
    	//设置发票相关参数信息
    	BillOrderTem billOrderTem = new BillOrderTem();
    	billOrderTem.setvATRegNumber("GB 281215228");
    	billOrderTem.setInvoiceNo("206-2063267-1430702");
    	billOrderTem.setInvoiceDate("2019-11-29");
    	billOrderTem.setBillTo("Matt Nolan");
    	billOrderTem.setAddress_1("上海市 Travel Ltd, 8 Corunna Court Corunna Road");
    	billOrderTem.setAddress_2("Warwick,Warwickshire");
    	billOrderTem.setAddress_3("CV345HQ,GB");
    	billOrderTem.setPhone("07584321011");
    	billOrderTem.seteMail("54ggsxkmrzyzhld@marketplace.amazon.co.uk");
    	billOrderTem.setInvoiceSubtotal("￡6.91");
    	billOrderTem.setvATRate("20.00%");
    	billOrderTem.setInvoiceTotal("￡8.29");
    	billOrderTem.setPostage("￡0.00");
    	billOrderTem.setSubtotal("￡6.91");
    	billOrderTem.setVat("￡1.38");
    	billOrderTem.setTotal("￡8.29");
    	List<BillOrderItemTem> billOrderItemTems = new ArrayList<>();
    	BillOrderItemTem billOrderItemTem = new BillOrderItemTem();
    	billOrderItemTem.setDescription("AllRight Clipboard Solid Office Document Holder Filling New");
    	billOrderItemTem.setQuantity("1");
    	billOrderItemTem.setUnitPrice("￡6.91");
    	billOrderItemTem.setAmount("￡6.91");
    	BillOrderItemTem billOrderItemTem2 = new BillOrderItemTem();
    	billOrderItemTem2.setDescription("Aluminium Defrost Tray Quick Thaw Rapid Defrosting Meat Other Foods Frozen Food[35.5 x 20.4 x0.3cm]");
    	billOrderItemTem2.setQuantity("2");
    	billOrderItemTem2.setUnitPrice("￡100.91");
    	billOrderItemTem2.setAmount("￡80.91");
    	BillOrderItemTem billOrderItemTem3 = new BillOrderItemTem();
    	billOrderItemTem3.setDescription("SET of Unisex Sports Sweatbands Head band Wrist Bands Cycling SWEAT BANDS UK[Red(1xHeadband   2xWristBands)]");
    	billOrderItemTem3.setQuantity("5");
    	billOrderItemTem3.setUnitPrice("￡9999.91");
    	billOrderItemTem3.setAmount("￡9999.91");
    	billOrderItemTems.add(billOrderItemTem);
    	billOrderItemTems.add(billOrderItemTem2);
    	billOrderItemTems.add(billOrderItemTem3);
    	billOrderTem.setBillOrderItemTems(billOrderItemTems);
    	
        File file = new File("d:/test3.pdf");
        //file.createNewFile();
        BasePDFWrite basePDFWrite =  new BasePDFWrite(file);
        basePDFWrite.obtainBillOrderPdf(billOrderTem);
        
        System.out.println("完成pdf生成");
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
