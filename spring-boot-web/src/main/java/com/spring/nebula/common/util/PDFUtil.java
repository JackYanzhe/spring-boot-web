package com.spring.nebula.common.util;

import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFUtil {
	private static final String FILE_DIR = "D:\\";
	public static void createPDF(String myString)throws Exception {
	//Step 1—Create a Document.  
	Rectangle pageSize = new Rectangle(100, 60);
	Document document = new Document(pageSize);  
	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "createSamplePDF.pdf"));  
	document.open();   
	Barcode128 code128 = new Barcode128();  
	code128.setCode(myString.trim());  
	code128.setCodeType(Barcode128.CODE128);  
	code128.setBarHeight(15.0f);
	code128.setX(0.7f);
	//条形码
	PdfContentByte cb = writer.getDirectContent();  
	Image code128Image = code128.createImageWithBarcode(cb, null, null);  
	code128Image.setAbsolutePosition(10,700);  
	code128Image.scalePercent(125);  
	document.add(code128Image);  
//	 二维码 
//	BarcodeQRCode qrcode = new BarcodeQRCode(myString.trim(), 1, 1, null);  
//	Image qrcodeImage = qrcode.getImage();  
//	qrcodeImage.setAbsolutePosition(10,600);  
//	qrcodeImage.scalePercent(200);  
//	document.add(qrcodeImage);  
	//Step 5—Close the Document.  
	document.close();  
	}
}
