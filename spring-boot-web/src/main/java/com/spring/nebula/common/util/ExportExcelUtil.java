package com.spring.nebula.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * ExportExcelUtil 
 * @author qiang.zhou
 * @version 1.0
 *
 */
public class ExportExcelUtil {
	
	/**
	 * 下载Excel
	 * @param response
	 * @param content 内容
	 * @param title 标题
	 * @param fileName 文件名
	 */
	public static void downloadExcel(HttpServletResponse response, List<LinkedHashMap<String, Object>> content,
			String[] title, String fileName) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			createWorkBook(content, title).write(os);
			exportExcel(response, os, fileName);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 生成本地Excel
	 * @param path 本地盘符URL
	 * @param content 内容
	 * @param title 标题
	 * @param fileName 文件名
	 */
	public static void createLocalExcel(String path, List<LinkedHashMap<String, Object>> content,String[] title, String fileName) {
		try {
			FileOutputStream outputStream = new  FileOutputStream(path+fileName+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +".xls");
			HSSFWorkbook workbook=createWorkBook(content, title);
            workbook.write(outputStream);
            workbook.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private static HSSFWorkbook createWorkBook(List<LinkedHashMap<String, Object>> content, String title[]) {
		// 获取key值
		Object[] objs = content.get(0).keySet().toArray();
		String[] keys = Arrays.asList(objs).toArray(new String[0]);

		// 创建excel工作簿
		 HSSFWorkbook workbook = new HSSFWorkbook();

		// 创建第一个sheet（页），并命名
		Sheet sheet = workbook.createSheet("Sheet1");
		// 定义样式并设置
		CellStyle cs = workbook.createCellStyle();
		CellStyle cs2 = workbook.createCellStyle();
		setStyle(keys, workbook, sheet, cs, cs2);
		// 生成表格
		generateTable(content, title, keys, workbook, sheet, cs, cs2, 0);
		setAutoWith(keys, sheet);
		return workbook;
	}

	private static void exportExcel(HttpServletResponse response, ByteArrayOutputStream os, String fileName) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
			ServletOutputStream out = response.getOutputStream();

			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void setStyle(String[] keys, HSSFWorkbook wb, Sheet sheet, CellStyle cs, CellStyle cs2) {
		// 创建两种字体
		Font f = wb.createFont();
		Font f2 = wb.createFont();
		// 创建第一种字体样式（用于列名）
		f.setFontHeightInPoints(new Short("12"));
		f.setColor(IndexedColors.BLACK.getIndex());
		f.setBold(true);
		// 创建第二种字体样式（用于值）
		f2.setFontHeightInPoints(new Short("11"));
		// f2.setColor(IndexedColors.BLACK.getIndex());
		// 设置第一种单元格的样式（用于列名）
		cs.setFont(f);
		cs.setBorderLeft(BorderStyle.THIN);
		cs.setBorderRight(BorderStyle.THIN);
		cs.setBorderTop(BorderStyle.THIN);
		cs.setBorderBottom(BorderStyle.THIN);
		cs.setAlignment(HorizontalAlignment.CENTER);
		// 设置第二种单元格的样式（用于值）
		cs2.setFont(f2);
		cs2.setBorderLeft(BorderStyle.THIN);
		cs2.setBorderRight(BorderStyle.THIN);
		cs2.setBorderTop(BorderStyle.THIN);
		cs2.setBorderBottom(BorderStyle.THIN);
		cs2.setAlignment(HorizontalAlignment.CENTER);
	}

	private static void generateTable(List<LinkedHashMap<String, Object>> content, String[] title, String[] keys,
			Workbook wb, Sheet sheet, CellStyle cs, CellStyle cs2, int start) {
		// 创建第一行
		Row row = sheet.createRow(start);
		// 设置列名
		for (int i = 0; i < title.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(cs);
		}
		// 设置每行每列的值
		for (short offset = 0; offset < content.size(); offset++) {
			// 创建一行，在页sheet上
			Row row1 = sheet.createRow(start + offset + 1);
			// 在row行上创建一个方格
			for (short j = 0; j < keys.length; j++) {
				Cell cell = row1.createCell(j);
				cell.setCellValue(
						content.get(offset).get(keys[j]) == null ? " " : content.get(offset).get(keys[j]).toString());
				cell.setCellStyle(cs2);
			}
		}
	}

	private static void setAutoWith(String[] keys, Sheet sheet) {
		for (int i = 0; i < keys.length; i++) {
			// 自适应宽度
			sheet.autoSizeColumn(i);
		}
	}

}
