package com.spring.nebula.api.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.spring.nebula.qrcode.vo.AccountInvoiceInfo;
import com.spring.nebula.qrcode.vo.BillOrderItemTem;
import com.spring.nebula.qrcode.vo.BillOrderTem;
import com.spring.nebula.util.BillPdfProduce;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(tags = "报表导出接口", value = "报表导出接口")
@RestController
@RequestMapping("/PoiExport")
public class PoiExportController {

	@ApiOperation(value = "obtainBillpdf接口", notes = "")
	@RequestMapping(value = "obtainBillpdf", method = RequestMethod.GET)
    public String obtainBillpdf(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "orderId",required = true) String orderId,@RequestParam(value = "accountId",required = true) String accountId) {
       
		String result = "生成成功";
        try {
        	System.out.println("参数1："+orderId+",参数2："+accountId);
        	 // 设置响应头，控制浏览器下载该文件
            response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode("amazon发票详情.pdf", "UTF-8"));
            // 创建输出流
            OutputStream out = response.getOutputStream();
            //BasePDFWrite basePDFWrite = new BasePDFWrite(out);
            //basePDFWrite.obtainBillOrderPdf(initBill());
            
            BillPdfProduce billPdfProduce = new BillPdfProduce();
            BillOrderTem initBill = initBill();
            AccountInvoiceInfo accountInvoiceInfo = new AccountInvoiceInfo();
            initBill.setAccountInvoiceInfo(accountInvoiceInfo);
            billPdfProduce.exportAmazonBillOrderPdf(initBill, out);
            
        } catch (Exception e) {
        	result="生成失败,异常原因："+e.getMessage();
            e.printStackTrace();
        }
        
        return result;
    }
	@RequestMapping("/testpdf")
    public void testpdf(HttpServletRequest request,HttpServletResponse response,ModelMap map) {
       
        try {
        	 // 设置响应头，控制浏览器下载该文件
            response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode("投保单详情.pdf", "UTF-8"));
            //常用的有paragraph段落、phrase语句块、chunk最小单位块
            BaseFont bfChinese = BaseFont.createFont("STSong-Light",
                        "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font fontChinese = new Font(bfChinese, 12, Font.NORMAL);

            
            Map<String,Object> mapInfo = new HashMap<String,Object>();
            init(mapInfo);
            
            // 创建输出流
            OutputStream out = response.getOutputStream();
            //Rectangle pageSize = new Rectangle(144, 720);
            //pageSize.BackgroundColor = new Color(0xFF, 0xFF, 0xDE);
            //pageSize.setBorderColor(new BaseColor(0xFF, 0xFF, 0xDE));

            Document doc = new Document();
            PdfWriter writer = PdfWriter.getInstance(doc, out);

            doc.open();
            /*Anchor anchorTarget = new Anchor("aaaa你谁");
            anchorTarget.setName("aa你好");
            Paragraph paragraph1 = new Paragraph();
            paragraph1.setSpacingBefore(50);
            paragraph1.add(anchorTarget);*/
            
            //-------------------第一行----------------------
            //段落文本
            Paragraph paragraphBlue = new Paragraph("", fontChinese);
            paragraphBlue.setLeading(20f);// 行间距
            paragraphBlue.setAlignment(Element.ALIGN_CENTER); 
            Font font1 = new Font(bfChinese, 12, Font.BOLD);
            
            Chunk chunk1 = new Chunk("预约单号：    "+"0009198181"+"      ");
            Chunk chunk2 = new Chunk("投保单号：    "+"8324735439"+"      ");
            Chunk chunk3 = new Chunk("投保单状态：    "+"拒保"+"      ");
            chunk1.setFont(font1);
            chunk2.setFont(font1);
            chunk3.setFont(font1);
            
            paragraphBlue.add(chunk1);  
            paragraphBlue.add(chunk2);  
            paragraphBlue.add(chunk3);  
            doc.add(paragraphBlue);
            
            //-------------------投保方案----------------------
            Font font2 = new Font(bfChinese, 16, Font.BOLD);
            Paragraph paragraphBlue1 = new Paragraph("", font2);
            Chunk titleChunk1 = new Chunk("投保方案",font2);
            //BaseColor.LIGHT_GRAY
            titleChunk1.setBackground(BaseColor.LIGHT_GRAY, 10, 10, 470, 10);
            paragraphBlue1.add(titleChunk1);
            paragraphBlue1.setSpacingBefore(20);
            doc.add(paragraphBlue1);
            
            Paragraph paragraphBlue2 = new Paragraph("尊享天下", font2);
            paragraphBlue2.setAlignment(Element.ALIGN_CENTER);
            paragraphBlue2.setSpacingBefore(20);
            doc.add(paragraphBlue2);
            
            //创建表格
            //PdfPTable table = new PdfPTable(6);
            PdfPTable table = new PdfPTable(5);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中  
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE); 
            table.getDefaultCell().setFixedHeight(30);
            table.getDefaultCell().setBorderWidth(1);// 边框宽度

            // 添加表头元素
            table.addCell(new Paragraph("险种名称", fontChinese));
            table.addCell(new Paragraph("保障期限", fontChinese));
            table.addCell(new Paragraph("缴费期限", fontChinese));
            table.addCell(new Paragraph("保险金额", fontChinese));
            table.addCell(new Paragraph("年缴保费", fontChinese));

            // 添加表格的内容
            table.addCell(new Paragraph("尊享人生", fontChinese));
            table.addCell(new Paragraph("20年", fontChinese));
            table.addCell(new Paragraph("趸交", fontChinese));
            table.addCell(new Paragraph("100000美元", fontChinese));
            table.addCell(new Paragraph("3421美元", fontChinese));

            table.setSpacingBefore(10f);// 设置表格上面空白宽度
            doc.add(table);

            //-------------------投保信息-------------------------
            Paragraph paragraphBlue3 = new Paragraph("", font2);
            Chunk titleChunk2 = new Chunk("投保信息",font2);
            //BaseColor.LIGHT_GRAY
            titleChunk2.setBackground(BaseColor.LIGHT_GRAY, 10, 10, 470, 10);
            paragraphBlue3.add(titleChunk2);
            paragraphBlue3.setSpacingBefore(20);
            doc.add(paragraphBlue3);
            
            //--------------------申请人--------------------------
            Font font3 = new Font(bfChinese, 14, Font.BOLD); //加粗14
            Paragraph paragraphBlue4 = new Paragraph("申请人", font3);
            paragraphBlue4.setSpacingBefore(20);
            doc.add(paragraphBlue4);
            Paragraph paragraphBlue6 = new Paragraph("", fontChinese);
            paragraphBlue6.setIndentationLeft(30); //左缩进
            paragraphBlue6.setLeading(30);// 行间距
            //paragraphBlue6.setAlignment(Element.ALIGN_CENTER);
            String str = "                                                            ";
            Chunk applyChunk1 = new Chunk("*姓：    "+"张"+"                  "+"*名：    "+"三"+"\n");
            //applyChunk1.setHorizontalScaling(50);
            Chunk applyChunk2 = new Chunk("*姓名拼音："+"ZHANG SAN"+str+"*性别："+"男"+"\n");
            Chunk applyChunk3 = new Chunk("*出生日期："+"1985年7月20日"+str+"*年龄："+"32周岁"+"\n");
            Chunk applyChunk4 = new Chunk("*身份证号码："+"110022198507202012"+str+"*通行证号码："+"C17646643"+"\n");
            Chunk applyChunk5 = new Chunk("*婚姻状况："+"未婚"+str+"*吸烟习惯："+"吸烟"+"\n");
            Chunk applyChunk6 = new Chunk("*身高："+"181 cm"+str+"*体重："+"76 kg"+"\n");
            Chunk applyChunk7 = new Chunk("*身份证地址："+"山东省济南市天桥区惠元里06号306"+"\n");
            Chunk applyChunk8 = new Chunk("*通讯地址："+"山东省济南市天桥区惠元里06号306"+"\n");
            Chunk applyChunk9 = new Chunk("住宅电话："+"13566358894"+str+"*手机号码："+"185*******7261"+"\n");
            Chunk applyChunk10 = new Chunk("*E-mail："+"12646@qq.com"+"\n");
            
            Chunk applyChunk11 = new Chunk("职业资料"+"\n");
            Chunk applyChunk12 = new Chunk("*公司名称："+"山东蓝翔技工学校"+"\n");
            Chunk applyChunk13 = new Chunk("*业务性质："+"教育培训"+str+"*工作职位："+"教学组长"+"\n");
            Chunk applyChunk14 = new Chunk("*公司地址："+"山东省济南市天桥区蓝翔中路06号"+"\n");
            
            Chunk applyChunk15 = new Chunk("收入来源(过去12个月总收入)"+"\n");
            Chunk applyChunk16 = new Chunk("*工资："+"1000000港元"+str+"*奖金："+"600000港元"+"\n");
            Chunk applyChunk17 = new Chunk("*自保件："+"否"+"\n");
            Chunk applyChunk18 = new Chunk("*是受保人的："+"父亲"+"\n");
            
            //样式
            applyChunk11.setFont(font3);
            applyChunk15.setFont(font3);
            
            paragraphBlue6.add(applyChunk1);
            paragraphBlue6.add(applyChunk2);
            paragraphBlue6.add(applyChunk3);
            paragraphBlue6.add(applyChunk4);
            paragraphBlue6.add(applyChunk5);
            paragraphBlue6.add(applyChunk6);
            paragraphBlue6.add(applyChunk7);
            paragraphBlue6.add(applyChunk8);
            paragraphBlue6.add(applyChunk9);
            paragraphBlue6.add(applyChunk10);
            paragraphBlue6.add(applyChunk11);
            paragraphBlue6.add(applyChunk12);
            paragraphBlue6.add(applyChunk13);
            paragraphBlue6.add(applyChunk14);
            paragraphBlue6.add(applyChunk15);
            paragraphBlue6.add(applyChunk16);
            paragraphBlue6.add(applyChunk17);
            paragraphBlue6.add(applyChunk18);
            doc.add(paragraphBlue6);
            
            //---------------受保人--------------
            Paragraph paragraphBlue5 = new Paragraph("受保人", font3);
            doc.add(paragraphBlue5);
            Paragraph paragraphBlue7 = new Paragraph("", fontChinese);
            paragraphBlue7.setIndentationLeft(30); //左缩进
            paragraphBlue7.setLeading(30); //行间距
            
            Chunk insuredChunk1 = new Chunk("*姓："+"张"+str+"*名："+"三"+"\n");
            Chunk insuredChunk2 = new Chunk("*姓名拼音："+"ZHANG SAN"+str+"*性别："+"男"+"\n");
            Chunk insuredChunk3 = new Chunk("*出生日期："+"1985年7月20日"+str+"*身份证号码："+"110022200807202012"+"\n");
            Chunk insuredChunk4 = new Chunk("*与申请人关系："+"父女"+str+"*吸烟习惯："+"非吸烟"+"\n");
            Chunk insuredChunk5 = new Chunk("*身份证地址："+"山东省济南市天桥区惠元里06号306"+"\n");
            Chunk insuredChunk6 = new Chunk("*通讯地址："+"山东省济南市天桥区惠元里06号306"+"\n");
            Chunk insuredChunk7 = new Chunk("职业及收入资料(如受保人非申请人本人，且满18周岁以上，请如实填写)"+"\n");
            Chunk insuredChunk8 = new Chunk("公司名称："+""+"\n");
            Chunk insuredChunk9 = new Chunk("业务性质："+""+str+"工作职位："+""+"\n");
            Chunk insuredChunk10 = new Chunk("公司地址："+""+"\n");
            Chunk insuredChunk11 = new Chunk("年收入："+""+"\n");
            //样式
            insuredChunk7.setFont(font3);
            paragraphBlue7.add(insuredChunk1);
            paragraphBlue7.add(insuredChunk2);
            paragraphBlue7.add(insuredChunk3);
            paragraphBlue7.add(insuredChunk4);
            paragraphBlue7.add(insuredChunk5);
            paragraphBlue7.add(insuredChunk6);
            paragraphBlue7.add(insuredChunk7);
            paragraphBlue7.add(insuredChunk8);
            paragraphBlue7.add(insuredChunk9);
            paragraphBlue7.add(insuredChunk10);
            paragraphBlue7.add(insuredChunk11);
            doc.add(paragraphBlue7);
            
            //-----------受益人------------
            Paragraph paragraphBlue8 = new Paragraph("受益人", font3);
            doc.add(paragraphBlue8);
            Paragraph paragraphBlue9 = new Paragraph("", fontChinese);
            paragraphBlue9.setIndentationLeft(30); //左缩进
            paragraphBlue9.setLeading(30); //行间距
            for(int i=0;i<3;i++){
                Chunk benefitChunk1 = new Chunk("*姓："+"张"+str+"*名："+"三"+"\n");
                Chunk benefitChunk2 = new Chunk("*与受保人关系："+"父女"+str+"*身份证号码："+"110022198507202012"+"\n");
                Chunk benefitChunk3 = new Chunk("*受益比例："+"100%"+"\n");
                
                paragraphBlue9.add(benefitChunk1);
                paragraphBlue9.add(benefitChunk2);
                paragraphBlue9.add(benefitChunk3);
            }
            doc.add(paragraphBlue9);
            
            //------------已投保之保险公司保单（如有则必须提供）--------------
            Paragraph paragraphBlue10 = new Paragraph("已投保之保险公司保单（如有则必须提供）", font3);
            doc.add(paragraphBlue10);
            Paragraph paragraphBlue11 = new Paragraph("", fontChinese);
            paragraphBlue11.setIndentationLeft(30); //左缩进
            paragraphBlue11.setLeading(30); //行间距
            
            Chunk policyChunk1 = new Chunk("保险公司："+"仁福香港保险中介有限公司"+str+"产品："+"尊享太平"+"\n");
            Chunk policyChunk2 = new Chunk("年缴保费："+"100000港元"+str+"申请日期："+"2017年2月20日"+"\n");
            
            paragraphBlue11.add(policyChunk1);
            paragraphBlue11.add(policyChunk2);
            doc.add(paragraphBlue11);
            
            //-----------预约签单信息------------
            Paragraph paragraphBlue12 = new Paragraph("", font2);
            Chunk titleChunk3 = new Chunk("预约签单信息",font2);
            //BaseColor.LIGHT_GRAY
            titleChunk3.setBackground(BaseColor.LIGHT_GRAY, 10, 10, 440, 10);
            paragraphBlue12.add(titleChunk3);
            paragraphBlue12.setSpacingBefore(20);
            doc.add(paragraphBlue12);
            
            Paragraph paragraphBlue13 = new Paragraph("", fontChinese);
            paragraphBlue13.setSpacingBefore(20);
            paragraphBlue13.setIndentationLeft(30); //左缩进
            paragraphBlue13.setLeading(30); //行间距
            
            Chunk reserveChunk1 = new Chunk("*大区："+"西北财富管理事业部"+str+"*营业部："+"第一营业部"+"\n");
            Chunk reserveChunk2 = new Chunk("*理顾工号："+"H011116"+str+"*理顾姓名："+"王朝"+"\n");
            Chunk reserveChunk3 = new Chunk("*IS工号："+"H017563"+str+"*IS姓名："+"张三丰"+"\n");
            Chunk reserveChunk4 = new Chunk("*预约签单时间："+"2017年10月20日"+"\n");
            Chunk reserveChunk5 = new Chunk("提交日期："+"2017年10月18日"+"\n");
            Chunk reserveChunk6 = new Chunk("投保日期："+"2017年10月22日"+"\n");
            Chunk reserveChunk7 = new Chunk("拒保日期："+"2017年10月25日"+"\n");
            
            paragraphBlue13.add(reserveChunk1);
            paragraphBlue13.add(reserveChunk2);
            paragraphBlue13.add(reserveChunk3);
            paragraphBlue13.add(reserveChunk4);
            paragraphBlue13.add(reserveChunk5);
            paragraphBlue13.add(reserveChunk6);
            paragraphBlue13.add(reserveChunk7);
            doc.add(paragraphBlue13);

            doc.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(1);
        }
        
        
    }
	
	
    
    private static void init(Map<String, Object> map) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        map.put("reversionId", "0009198181");
        map.put("proposalNo", "8324735439");
        map.put("proposalStatus", "拒保");
        map.put("riskName", "尊享人生");
        map.put("baozhang", "20年");
        map.put("jiaofei", "趸交");
        map.put("jine", "100000美元");
        map.put("baofei", "3421美元");
        map.put("xing1", "张");
        map.put("ming1", "三");
        map.put("pinyin", "ZHANG SAN");
        map.put("sex", "男");
        map.put("xing2", "张");
        map.put("ming2", "小六");
        map.put("xing3", "张");
        map.put("ming3", "三");
        map.put("company", "仁福香港保险中介有限公司");
        map.put("area", "西北财富管理事业部");
        map.put("dept", "第一营业部");
        map.put("date", sdf.format(new Date()));
    }
    
    private  BillOrderTem initBill() {
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
    	return billOrderTem;
    }
}
