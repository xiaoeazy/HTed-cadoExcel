package com.huan.HTed.cado.util;  
  
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
  
  
public class ExcelUtil {  
      
    private final static String excel2003L =".xls";    //2003- 版本的excel  
    private final static String excel2007U =".xlsx";   //2007+ 版本的excel  
      
    /** 
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象 
     * @param in,fileName 
     * @return 
     * @throws IOException  
     */  
    public  List<List<Object>> getBankListByExcel(InputStream in,String fileName) throws Exception{  
        List<List<Object>> list = null;
        Workbook work = null;
		try {
			//创建Excel工作薄  
			work = this.getWorkbook(in,fileName);  
			if(null == work){  
			    throw new Exception("创建Excel工作薄为空！");  
			}  
			System.out.println("开始获取Excel完成");
			Sheet sheet = null;  
			Row row = null;  
			Cell cell = null;  
			  
			list = new ArrayList<List<Object>>();
			int workSheets =work.getNumberOfSheets();
			workSheets=1; // 暂时制定只要获取第一个sheet的数据
			//遍历Excel中所有的sheet  
			for (int i = 0; i < workSheets; i++) {  
			    sheet = work.getSheetAt(i);  
			    if(sheet==null){continue;}  
			      
			    //遍历当前sheet中的所有行  
			    for (int j = sheet.getFirstRowNum(); j < sheet.getLastRowNum(); j++) {  
			        row = sheet.getRow(j);  
			        if(row==null||row.getFirstCellNum()==j){continue;}  
			          
			        //遍历所有的列  
			        List<Object> li = new ArrayList<Object>();  
			        for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {  
			            cell = row.getCell(y);  
			            li.add(this.getCellValue(cell));  
			        }  
			        list.add(li);  
			    }  
			}
			System.out.println("获取Excel完成");
		}finally{
			if(work!=null)
				work.close();  
		}
       
        return list;  
    }  
      
    /** 
     * 描述：根据文件后缀，自适应上传文件的版本  
     * @param inStr,fileName 
     * @return 
     * @throws Exception 
     */  
    public  Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{  
        Workbook wb = null;  
        String fileType = fileName.substring(fileName.lastIndexOf("."));  
        if(excel2003L.equals(fileType)){  
            wb = new HSSFWorkbook(inStr);  //2003-  
        }else if(excel2007U.equals(fileType)){  
            wb = new XSSFWorkbook(inStr);  //2007+  
        }else{  
            throw new Exception("解析的文件格式有误！");  
        }  
        return wb;  
    }  
  
    /** 
     * 描述：对表格中数值进行格式化 
     * @param cell 
     * @return 
     */  
    public  Object getCellValue(Cell cell){  
        Object value = null;  
        DecimalFormat df = new DecimalFormat("0");  //格式化number String字符  
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化  
        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字  
          
        switch (cell.getCellType()) {  
        case Cell.CELL_TYPE_STRING:  
            value = cell.getRichStringCellValue().getString();  
            break;  
        case Cell.CELL_TYPE_NUMERIC:  
            if("General".equals(cell.getCellStyle().getDataFormatString())){  
                value = df.format(cell.getNumericCellValue());  
            }else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){  
                value = sdf.format(cell.getDateCellValue());  
            }else{  
                value = df2.format(cell.getNumericCellValue());  
            }  
            break;  
        case Cell.CELL_TYPE_BOOLEAN:  
            value = cell.getBooleanCellValue();  
            break;  
        case Cell.CELL_TYPE_BLANK:  
            value = "";  
            break;  
        default:  
            break;  
        }  
        return value;  
    }  
      
    
    public static Workbook createWorkBook(List<Map<String, Object>> list, String []keys, String columnNames[]) {
        // 创建excel工作簿
        Workbook wb = new XSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        //设置筛选
        CellRangeAddress AL = CellRangeAddress.valueOf("A1:AN1");
        sheet.setAutoFilter(AL);
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<keys.length;i++){
            setColumnWidth(sheet, i);//==================================>>
        }
       

        float rowheight = 25.5f;
        // 创建第一行
        Row row = sheet.createRow((short) 0);
        row.setHeightInPoints(rowheight);
        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs1 = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
        CellStyle cs3 = wb.createCellStyle();

        // 创建一种字体
        Font f = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 11);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setFontName("宋体");
        
        // 设置第一种单元格的样式（基本信息填充）
        cs.setFont(f);
        cs.setAlignment(CellStyle.ALIGN_LEFT);
        cs.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        
        // 设置第一种单元格的样式（用于基本信息）
        cs1.setFont(f);
        cs1.setBorderLeft(CellStyle.BORDER_THIN);
        cs1.setBorderRight(CellStyle.BORDER_THIN);
        cs1.setBorderTop(CellStyle.BORDER_THIN);
        cs1.setBorderBottom(CellStyle.BORDER_THIN);
        cs1.setAlignment(CellStyle.ALIGN_LEFT);
        cs1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cs1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cs1.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);

        // 设置第二种单元格的样式（用于cado修改）
        cs2.setFont(f);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_LEFT);
        cs2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cs2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cs2.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        
        // 设置第三种单元格的样式（用于银行修改）
        cs3.setFont(f);
        cs3.setBorderLeft(CellStyle.BORDER_THIN);
        cs3.setBorderRight(CellStyle.BORDER_THIN);
        cs3.setBorderTop(CellStyle.BORDER_THIN);
        cs3.setBorderBottom(CellStyle.BORDER_THIN);
        cs3.setAlignment(CellStyle.ALIGN_LEFT);
        cs3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cs3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cs3.setFillForegroundColor(HSSFColor.GOLD.index);
        
        //设置列名
        for(int i=0;i<columnNames.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            setCellStyle(i,cell,keys,cs,cs1,cs2,cs3,true);//==================================>>
        }
        //设置每行每列的值
        for (short i = 1; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short) i);
            row1.setHeightInPoints(rowheight);
            // 在row行上创建一个方格
            for(short j=0;j<keys.length;j++){
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i).get(keys[j]) == null?" ": list.get(i).get(keys[j]).toString());
                setCellStyle(j,cell,keys,cs,cs1,cs2,cs3,false);//==================================>>
            }
        }
        return wb;
    }
    
	private static void setColumnWidth(Sheet sheet ,int i){
		switch(i){
			case 0://a
	            sheet.setColumnWidth((short) i, (short) (8.22 * 256));
				break;
			case 1://b
				sheet.setColumnWidth((short) i, (short) (11 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 2://c
				sheet.setColumnWidth((short) i, (short) (21.89 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 3://d
				sheet.setColumnWidth((short) i, (short) (13.11 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 4://e
				sheet.setColumnWidth((short) i, (short) (15.33 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 5://f
				sheet.setColumnWidth((short) i, (short) (19.67 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 6://g
				sheet.setColumnWidth((short) i, (short) (8.22 * 256));
				break;
			case 7://h
				sheet.setColumnWidth((short) i, (short) (13.11 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 8://i
				sheet.setColumnWidth((short) i, (short) (15.33 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 9://j
				sheet.setColumnWidth((short) i, (short) (15.33 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 10://k
				sheet.setColumnWidth((short) i, (short) (11.33 * 256));
				break;
			case 11://l
				sheet.setColumnWidth((short) i, (short) (8.22 * 256));
				break;
			case 12://m
				sheet.setColumnWidth((short) i, (short) (7 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 13://n
				sheet.setColumnWidth((short) i, (short) (53.67 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 14://o
				sheet.setColumnWidth((short) i, (short) (14.22 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 15://p
				sheet.setColumnWidth((short) i, (short) (15.33 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 16://q
				sheet.setColumnWidth((short) i, (short) (12 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 17://r
				sheet.setColumnWidth((short) i, (short) (9 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 18://s
				sheet.setColumnWidth((short) i, (short) (16.44 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 19://t
				sheet.setColumnWidth((short) i, (short) (15.33 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 20://u
				sheet.setColumnWidth((short) i, (short) (15.33 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 21://v
				sheet.setColumnWidth((short) i, (short) (14.22 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 22://w
				sheet.setColumnWidth((short) i, (short) (11 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 23://x
				sheet.setColumnWidth((short) i, (short) (11 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 24://y
				sheet.setColumnWidth((short) i, (short) (19.67 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 25://z
				sheet.setColumnWidth((short) i, (short) (24.11 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 26://aa
				sheet.setColumnWidth((short) i, (short) (14.22 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 27://ab
				sheet.setColumnWidth((short) i, (short) (13.11 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 28://ac
				sheet.setColumnWidth((short) i, (short) (11 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 29://ad
				sheet.setColumnWidth((short) i, (short) (41.67 * 256));
				sheet.setColumnHidden((short)i, true);
				break;
			case 30://ae
				sheet.setColumnWidth((short) i, (short) (19.56 * 256));
				break;
			case 31://af
				sheet.setColumnWidth((short) i, (short) (14.89 * 256));
				break;
			case 32://ag
				sheet.setColumnWidth((short) i, (short) (14.89 * 256));
				break;
			case 33://ah
				sheet.setColumnWidth((short) i, (short) (14.89 * 256));
				break;
			case 34://ai
				sheet.setColumnWidth((short) i, (short) (14.89 * 256));
				break;
			case 35://aj
				sheet.setColumnWidth((short) i, (short) (14.89 * 256));
				break;
			case 36://ak
				sheet.setColumnWidth((short) i, (short) (14.89 * 256));
				break;
			case 37://al
				sheet.setColumnWidth((short) i, (short) (14.89 * 256)); 
				break;
			case 38://am
				sheet.setColumnWidth((short) i, (short) (14.89 * 256));
				break;
			case 39://an
				sheet.setColumnWidth((short) i, (short) (49.89 * 256));
				break;
			default:
				sheet.setColumnWidth((short) i, (short) (35.7 * 256));
				break;
		}
	}
    private static void setCellStyle(int j ,Cell cell,String [] keys,CellStyle cs,CellStyle cs1,CellStyle cs2,CellStyle cs3,boolean isTitle){
    	 if(j<=29){
    		if(isTitle)
    			cell.setCellStyle(cs1);
    		else
    			cell.setCellStyle(cs);
    	 }else if(j<=36&&j>29){
			cell.setCellStyle(cs2);
         }else{
         	cell.setCellStyle(cs3);
         }
    	// cell.setWrapText(true);
    }
}  