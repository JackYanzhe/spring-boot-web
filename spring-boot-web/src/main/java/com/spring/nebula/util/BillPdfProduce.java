package com.spring.nebula.util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Base64Utils;

import com.alibaba.druid.util.Base64;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.spring.nebula.qrcode.vo.AccountInvoiceInfo;
import com.spring.nebula.qrcode.vo.BillOrderItemTem;
import com.spring.nebula.qrcode.vo.BillOrderTem;
import com.spring.nebula.qrcode.vo.ParamItext;
public class BillPdfProduce {
	 private static Font headFont;
	 private static Font contentTitleFont;
	 private static Font contentFont;
	 private static ParamItext paramItext;
	 private static ParamItext paramHeadItext;
	 private static ParamItext paramItext2;
	 private static ParamItext paramItext3;
	 
	 
	 private static final String fontPath="D:\\111\\font\\MSYH.TTF";
	 private static final Integer BILL_SHOW_COMPANY_IS=1;
    public BillPdfProduce() {
		super();
	}
	/**
	 * 设置静态代码块（公用属性）
	 */
    static{
        BaseFont bfChinese_H;
        try {
        	//设置背景色    INVOICE:0,175,80;INVOICE背景色：249,190,143;Quantity背景色：215,227,187;￡6.91背景色：196,188,150
        	BaseColor headColor = new BaseColor(249,190,143);
        	BaseColor colColor1 = new BaseColor(215,227,187);
        	BaseColor colColor2 = new BaseColor(196,188,150);
        	//--------------------------------------------------------------------------------------------------
        	/*InputStream simsunFontFile = BillPdfProduce.class.getResourceAsStream("/staticfile/font/MSYH.TTF");
        	byte[] inputStream2byte = BasePdfWriteUtil.inputStream2byte(simsunFontFile);
       	    bfChinese_H = BaseFont.createFont("MSYH.asf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, true, inputStream2byte, null);*/
       	   
        	//bfChinese_H = BaseFont.createFont(fontPath,BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
       	    
        	bfChinese_H = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
       	    //--------------------------------------------------------------------------------------------------
            headFont = new Font(bfChinese_H, 24, Font.BOLD);
            //设置字体颜色
            headFont.setColor(new BaseColor(0,175,80));
            contentTitleFont = new Font(bfChinese_H, 10, Font.BOLD);
            contentFont = new Font(bfChinese_H, 10, Font.NORMAL);
            paramItext = new ParamItext(0);
            //设置单元格边框信息及背景色
            paramHeadItext = new ParamItext(0, 0, 0, 1);
            paramHeadItext.setBaseColor(headColor);
            //背景色
            paramItext2 = new ParamItext(colColor2);
            //背景色
            paramItext3 = new ParamItext(colColor1);
            
        } catch (Exception e) {
        	
            e.printStackTrace();
        }
    }
    
    /**
     * 导出pdf
     * @param billOrderTem
     * @throws Exception
     */
    public void exportAmazonBillOrderPdf(BillOrderTem billOrderTem,OutputStream out) {
    	Document document = null;
    	try {
    		AccountInvoiceInfo accountInvoiceInfo = billOrderTem.getAccountInvoiceInfo();
    		Integer isShowCompany = accountInvoiceInfo.getIsShowCompany();
    		
    		 //自定义纸张 长：40cm,宽：20
            Rectangle rectPageSize = new Rectangle(560, 850);
            // 定义A4页面大小
            //Rectangle rectPageSize = new Rectangle(PageSize.A4);
            // 实现页面的横置
            rectPageSize = rectPageSize.rotate();
            document = new Document(rectPageSize,80, 80, 10, 20);
            //设置输出流
    		PdfWriter.getInstance(document,out);
    		
            document.open();
        	List<BillOrderItemTem> billOrderItemList = billOrderTem.getBillOrderItemTems();
        	//加入一行空格
        	Paragraph blankRow = new Paragraph(11f, " ");
            document.add(blankRow);
            //页头信息并设置背景颜色
            //表格信息(按照宽度比)
            float[] widths = {16f,13f,18f,14f,34f,8f};
            PdfPTable table = BasePdfWriteUtil.createTable(widths);
           
            table.addCell(BasePdfWriteUtil.createParamCell("INVOICE", headFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,6,2,paramHeadItext));
            table.addCell(BasePdfWriteUtil.createCell("VAT Reg Number", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell(billOrderTem.getvATRegNumber(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            
            //此处进行判断是否需要显示公司相关信息
            if (BILL_SHOW_COMPANY_IS==isShowCompany) {
				//此时为展示公司地址信息
            	 table = requireBusinessInfoBill(table, billOrderTem);
			}else {
				 table = noRequireBusinessInfoBill(table, billOrderTem);
			}
           
            //此处为公用设置
            table.addCell(BasePdfWriteUtil.createParamCell("Description", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1,paramItext3));
            table.addCell(BasePdfWriteUtil.createParamCell("Quantity", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1,paramItext3));
            table.addCell(BasePdfWriteUtil.createParamCell("Unit Price", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1,paramItext3));
            table.addCell(BasePdfWriteUtil.createParamCell("Amount", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1,paramItext3));
            
            //此处写item描述相关信息
            if (null!=billOrderItemList) {
            	for (BillOrderItemTem billOrderItemTem : billOrderItemList) {
            		table.addCell(BasePdfWriteUtil.createCell(billOrderItemTem.getDescription(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1));
                    table.addCell(BasePdfWriteUtil.createCell(billOrderItemTem.getQuantity(), contentFont, Element.ALIGN_BOTTOM, Element.ALIGN_CENTER,1,1));
                    table.addCell(BasePdfWriteUtil.createCell(billOrderItemTem.getUnitPrice(), contentFont, Element.ALIGN_BOTTOM, Element.ALIGN_RIGHT,1,1));
                    table.addCell(BasePdfWriteUtil.createCell(billOrderItemTem.getAmount(), contentFont, Element.ALIGN_BOTTOM, Element.ALIGN_RIGHT,1,1));
            		
            		
        		}
    		}
            //留出一行空值，方便显示好看
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
            
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell("Postage", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
            table.addCell(BasePdfWriteUtil.createParamCell(billOrderTem.getPostage(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1,paramItext2));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell("Subtotal", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
            table.addCell(BasePdfWriteUtil.createParamCell(billOrderTem.getSubtotal(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1,paramItext2));
            
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell("VAT", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
            table.addCell(BasePdfWriteUtil.createParamCell(billOrderTem.getVat(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1,paramItext2));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell("Total", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
            table.addCell(BasePdfWriteUtil.createParamCell(billOrderTem.getTotal(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1,paramItext2));
            document.add(table);
            document.newPage();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			if (document != null){
                document.close();
            }
		}
    	
        
    }
    /**
     * 生成pdf流字符串并返回
     * @param billOrderTem
     * @throws Exception
     */
    public String obtainAmazonBillOrderPdf(BillOrderTem billOrderTem) {
    	ByteArrayOutputStream byteArrayOutputStream = null;
    	Document document = null;
    	String billPdf = "";
    	try {
    		AccountInvoiceInfo accountInvoiceInfo = billOrderTem.getAccountInvoiceInfo();
    		Integer isShowCompany = accountInvoiceInfo.getIsShowCompany();
    		 //自定义纸张 长：40cm,宽：20
            Rectangle rectPageSize = new Rectangle(560, 850);
            // 定义A4页面大小
            //Rectangle rectPageSize = new Rectangle(PageSize.A4);
            // 实现页面的横置
            rectPageSize = rectPageSize.rotate();
            document = new Document(rectPageSize,80, 80, 10, 20);
    		//设置文件流
    		byteArrayOutputStream = new ByteArrayOutputStream();
    		PdfWriter.getInstance(document,byteArrayOutputStream);
            document.open();
            
        	List<BillOrderItemTem> billOrderItemList = billOrderTem.getBillOrderItemTems();
        	
        	//加入一行空格
        	Paragraph blankRow = new Paragraph(11f, " ");
            document.add(blankRow);
            //页头信息并设置背景颜色
            //表格信息(按照宽度比)
            float[] widths = {16f,13f,18f,14f,34f,8f};
            PdfPTable table = BasePdfWriteUtil.createTable(widths);
            table.addCell(BasePdfWriteUtil.createParamCell("INVOICE", headFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,6,2,paramHeadItext));
            table.addCell(BasePdfWriteUtil.createCell("VAT Reg Number", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell(billOrderTem.getvATRegNumber(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            //此处进行判断是否显示公司信息
            //此处进行判断是否需要显示公司相关信息
            if (BILL_SHOW_COMPANY_IS==isShowCompany) {
				//此时为展示公司地址信息
            	 table = requireBusinessInfoBill(table, billOrderTem);
			}else {
				 table = noRequireBusinessInfoBill(table, billOrderTem);
			}
            
            table.addCell(BasePdfWriteUtil.createParamCell("Description", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1,paramItext3));
            table.addCell(BasePdfWriteUtil.createParamCell("Quantity", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1,paramItext3));
            table.addCell(BasePdfWriteUtil.createParamCell("Unit Price", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1,paramItext3));
            table.addCell(BasePdfWriteUtil.createParamCell("Amount", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1,paramItext3));
            
            //此处写item描述相关信息
            if (null!=billOrderItemList) {
            	for (BillOrderItemTem billOrderItemTem : billOrderItemList) {
            		table.addCell(BasePdfWriteUtil.createCell(billOrderItemTem.getDescription(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1));
                    table.addCell(BasePdfWriteUtil.createCell(billOrderItemTem.getQuantity(), contentFont, Element.ALIGN_BOTTOM, Element.ALIGN_CENTER,1,1));
                    table.addCell(BasePdfWriteUtil.createCell(billOrderItemTem.getUnitPrice(), contentFont, Element.ALIGN_BOTTOM, Element.ALIGN_RIGHT,1,1));
                    table.addCell(BasePdfWriteUtil.createCell(billOrderItemTem.getAmount(), contentFont, Element.ALIGN_BOTTOM, Element.ALIGN_RIGHT,1,1));
            		
            		
        		}
    		}
            //留出一行空值，方便显示好看
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
            
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell("Postage", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
            table.addCell(BasePdfWriteUtil.createParamCell(billOrderTem.getPostage(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1,paramItext2));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell("Subtotal", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
            table.addCell(BasePdfWriteUtil.createParamCell(billOrderTem.getSubtotal(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1,paramItext2));
            
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell("VAT", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
            table.addCell(BasePdfWriteUtil.createParamCell(billOrderTem.getVat(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1,paramItext2));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1));
            table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
            table.addCell(BasePdfWriteUtil.createCell("Total", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
            table.addCell(BasePdfWriteUtil.createParamCell(billOrderTem.getTotal(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1,paramItext2));
            document.add(table);
            document.newPage();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			if (document != null){
                document.close();
            }
		}
    	//生成流字符串
    	billPdf = generateByteStreamStr(byteArrayOutputStream);
    	
    	return billPdf;
        
    }
    
    /**
     * 不需要显示地址的bill
     * @param table
     * @param billOrderTem
     * @return
     */
    public PdfPTable noRequireBusinessInfoBill(PdfPTable table,BillOrderTem billOrderTem) {
    	table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,16));
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,16));
        
        table.addCell(BasePdfWriteUtil.createCell("Invoice No.", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(BasePdfWriteUtil.createCell(billOrderTem.getInvoiceNo(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(BasePdfWriteUtil.createCell("Invoice Date", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(BasePdfWriteUtil.createCell(billOrderTem.getInvoiceDate(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        
        table.addCell(BasePdfWriteUtil.createCell("Bill To", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(BasePdfWriteUtil.createCell(billOrderTem.getBillTo(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(BasePdfWriteUtil.createCell("Address", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,4));
        
        table.addCell(BasePdfWriteUtil.createParamCell(billOrderTem.getAddress_1(), contentFont, Element.ALIGN_LEFT, Element.ALIGN_LEFT,1,2,paramItext));
        table.addCell(BasePdfWriteUtil.createParamCell(billOrderTem.getAddress_2(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1,paramItext));
        table.addCell(BasePdfWriteUtil.createParamCell(billOrderTem.getAddress_3(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1,paramItext));

        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(BasePdfWriteUtil.createCell("Phone", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(BasePdfWriteUtil.createCell(billOrderTem.getPhone(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(BasePdfWriteUtil.createCell("E-Mail", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(BasePdfWriteUtil.createCell(billOrderTem.geteMail(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        
        table.addCell(BasePdfWriteUtil.createParamCell("Invoice Subtotal", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1,paramItext2));
        table.addCell(BasePdfWriteUtil.createCell(billOrderTem.getInvoiceSubtotal(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(BasePdfWriteUtil.createCell("VAT Rate", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(BasePdfWriteUtil.createCell(billOrderTem.getvATRate(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        
        
        
        table.addCell(BasePdfWriteUtil.createParamCell("Invoice Total", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1,paramItext2));
        table.addCell(BasePdfWriteUtil.createCell(billOrderTem.getInvoiceTotal(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
    	
    	return table;
    }
    
    /**
     * 需要显示地址的bill
     * @param table
     * @param billOrderTem
     * @return
     */
    public PdfPTable requireBusinessInfoBill(PdfPTable table,BillOrderTem billOrderTem) {
    	
    	//Business Information
    	table.addCell(BasePdfWriteUtil.createCell(" ", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
    	table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,2,1));
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,16));
        
       
        table.addCell(BasePdfWriteUtil.createCell("Business Information", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,12));
        //账户email
        table.addCell(BasePdfWriteUtil.createCell(billOrderTem.getAccountEmail(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,2,1));
        table.addCell(BasePdfWriteUtil.createCell("Invoice No.", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(BasePdfWriteUtil.createCell(billOrderTem.getInvoiceNo(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        //公司名称
        table.addCell(BasePdfWriteUtil.createCell(billOrderTem.getCompanyName(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,2,2));
        table.addCell(BasePdfWriteUtil.createCell("Invoice Date", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(BasePdfWriteUtil.createCell(billOrderTem.getInvoiceDate(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(BasePdfWriteUtil.createCell("Bill To", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(BasePdfWriteUtil.createCell(billOrderTem.getBillTo(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        
        //公司地址
        table.addCell(BasePdfWriteUtil.createCell(billOrderTem.getCompanyAddress(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,2,9));
        table.addCell(BasePdfWriteUtil.createCell("Address", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,4));
        table.addCell(BasePdfWriteUtil.createParamCell(billOrderTem.getAddress_1(), contentFont, Element.ALIGN_LEFT, Element.ALIGN_LEFT,1,2,paramItext));
        table.addCell(BasePdfWriteUtil.createParamCell(billOrderTem.getAddress_2(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1,paramItext));
        table.addCell(BasePdfWriteUtil.createParamCell(billOrderTem.getAddress_3(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1,paramItext));
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        
        table.addCell(BasePdfWriteUtil.createCell("Phone", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(BasePdfWriteUtil.createCell(billOrderTem.getPhone(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        
        table.addCell(BasePdfWriteUtil.createCell("E-Mail", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        table.addCell(BasePdfWriteUtil.createCell(billOrderTem.geteMail(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        
        table.addCell(BasePdfWriteUtil.createParamCell("Invoice Subtotal", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1,paramItext2));
        table.addCell(BasePdfWriteUtil.createCell(billOrderTem.getInvoiceSubtotal(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
       
      
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,2,1));
        table.addCell(BasePdfWriteUtil.createCell("VAT Rate", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(BasePdfWriteUtil.createCell(billOrderTem.getvATRate(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
       
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,2,1));
        table.addCell(BasePdfWriteUtil.createParamCell("Invoice Total", contentTitleFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1,paramItext2));
        table.addCell(BasePdfWriteUtil.createCell(billOrderTem.getInvoiceTotal(), contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,1,1));
        
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,2,1));
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(BasePdfWriteUtil.createCell(" ", contentFont, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        

    	return table;
    	
    }
    
    
    
    /**
     * 生成64位流字符串
     */
    public String generateByteStreamStr(ByteArrayOutputStream byteArrayOutputStream) {
    	String billPdf ="";
    	try {
            if (byteArrayOutputStream != null){
                // 将流转化成base64编码的字符串
                byte[] bs = byteArrayOutputStream.toByteArray();
                billPdf = Base64.byteArrayToBase64(bs);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    	return billPdf;
    	
    }
    
    
    
    /**
     * 将base64编码转换成PDF
     *
     * @param base64sString
     *            1.使用BASE64Decoder对编码的字符串解码成字节数组
     *            2.使用底层输入流ByteArrayInputStream对象从字节数组中获取数据；
     *            3.建立从底层输入流中读取数据的BufferedInputStream缓冲输出流对象；
     *            4.使用BufferedOutputStream和FileOutputSteam输出数据到指定的文件中
     */
    public static void base64StringToPDF(String base64sString,String filePath) {
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try {
            // 将base64编码的字符串解码成字节数组
            byte[] bytes = Base64Utils.decodeFromString(base64sString);
            // 创建一个将bytes作为其缓冲区的ByteArrayInputStream对象
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            // 创建从底层输入流中读取数据的缓冲输入流对象
            bin = new BufferedInputStream(bais);
            // 指定输出的文件
            File file = new File(filePath);
            // 创建到指定文件的输出流
            fout = new FileOutputStream(file);
            // 为文件输出流对接缓冲输出流对象
            bout = new BufferedOutputStream(fout);
            byte[] buffers = new byte[1024];
            int len = bin.read(buffers);
            while (len != -1) {
                bout.write(buffers, 0, len);
                len = bin.read(buffers);
            }
            // 刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
            bout.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bin != null) {
                    bin.close();
                }
                if (fout != null) {
                    fout.close();
                }
                if (bout != null) {
                    bout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
    	//设置发票相关参数信息
    	BillOrderTem billOrderTem = new BillOrderTem();
    	billOrderTem.setvATRegNumber("GB 281215228");
    	billOrderTem.setInvoiceNo("206-2063267-1430702");
    	billOrderTem.setInvoiceDate("2019-11-29");
    	billOrderTem.setBillTo("Matt Nolan");
    	billOrderTem.setAddress_1("Scandinavian Travel Ltd, 8 Corunna Court Corunna Road");
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
    	billOrderItemTem2.setDescription("上海Aluminium Defrost Tray Quick Thaw Rapid Defrosting Meat Other Foods Frozen Food[35.5 x 20.4 x0.3cm]");
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
    	AccountInvoiceInfo accountInvoiceInfo = new AccountInvoiceInfo();
    	billOrderTem.setAccountInvoiceInfo(accountInvoiceInfo);
    	
        //File file = new File("d:/test2.pdf");
        String fontPath ="D:\\111\\font\\MSYH.TTF";
        //file.createNewFile();
        BillPdfProduce basePDFWrite =  new BillPdfProduce();
        //文件流
        String obtainAmazonBillOrderPdf = basePDFWrite.obtainAmazonBillOrderPdf(billOrderTem);
        if (!"".equals(obtainAmazonBillOrderPdf)) {
        	//通过流的方式进行生成pdf文件
        	base64StringToPDF(obtainAmazonBillOrderPdf, "d:/test4.pdf");
		}
        
        System.out.println("完成pdf生成");
    }
}
