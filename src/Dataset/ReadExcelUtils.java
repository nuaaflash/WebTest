package Dataset;
import java.io.FileInputStream; 

 
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;  
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;  
  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.DateUtil;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;

import Algorithm.RBF;
import Sql.Sql;
  
/** 
 * 读取Excel 
 *  
 * 
 */  
public class ReadExcelUtils {  
    private Logger logger = LoggerFactory.getLogger(ReadExcelUtils.class);  
    private Workbook wb;  
    private Sheet sheet;  
    private Row row; 
    private String filepath; 
    private static ReadExcelUtils single = null;
    
    public ReadExcelUtils(){}
	
	public static ReadExcelUtils getInstance() {  
		if (single == null) {    
		      single = new ReadExcelUtils();  
		 }    
		return single;  
	}
    
    public void test () {  
        try {  
            
        	ReadExcelUtils excelReader = new ReadExcelUtils();  
            Map<Integer, Map<Integer,Object>> map = excelReader.readExcelContent();  
            System.out.println("获得Excel表格的内容:");  
            for (int i = 1; i <= map.size(); i++) {  
                System.out.println(map.get(i));  
            }  
       } catch (FileNotFoundException e) {  
            System.out.println("未找到指定路径的文件!");  
            e.printStackTrace();  
        }catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    public void setFilepath(String path){
    	filepath = path;
   	 	ReadExcel(filepath);
    }
    
    public void ReadExcel(String filepath) {  
        if(filepath==null){  
            return;  
        }  
        String ext = filepath.substring(filepath.lastIndexOf("."));  
        try {  
            InputStream is = new FileInputStream(filepath); 
            System.out.println(filepath);
            if(".xls".equals(ext)){  
                wb = new HSSFWorkbook(is);  
            }else if(".xlsx".equals(ext)){  
                wb = new XSSFWorkbook(is);  
            }else{  
                wb=null;  
            }  
        } catch (FileNotFoundException e) {  
            logger.error("FileNotFoundException", e);  
        } catch (IOException e) {  
            logger.error("IOException", e);  
        }  
    }

      
    /** 
     * 读取Excel表格表头的内容 
     *  
     * @param InputStream 
     * @return String 表头内容的数组 
     * @author zengwendong 
     */  
    public ArrayList<String> readExcelTitle() throws Exception{  
        if(wb==null){  
            throw new Exception("Workbook对象为空！");  
        }  
        sheet = wb.getSheetAt(0);  
        row = sheet.getRow(0);  
        // 标题总列数  
        int colNum = row.getPhysicalNumberOfCells();   
        ArrayList<String> title = new ArrayList<String>();  
        for (int i = 1; i < colNum; i++) {  
            // title[i] = getStringCellValue(row.getCell((short) i));  
            title.add((String)getCellFormatValue(row.getCell(i)));  
        }  
        return title;  
    }  
  
    /** 
     * 读取Excel数据内容 
     *  
     * @param InputStream 
     * @return Map 包含单元格数据内容的Map对象 
     * @author zengwendong 
     */  
    public Map<Integer, Map<Integer,Object>> readExcelContent() throws Exception{  
        if(wb==null){  
            throw new Exception("Workbook对象为空！");  
        }  
        Map<Integer, Map<Integer,Object>> content = new HashMap<Integer, Map<Integer,Object>>();  
          
        sheet = wb.getSheetAt(0);  
        // 得到总行数  
        int rowNum = sheet.getLastRowNum();  
        row = sheet.getRow(0);  
        int colNum = row.getPhysicalNumberOfCells();  
        // 正文内容应该从第二行开始,第一行为表头的标题  
        for (int i = 1; i <= rowNum; i++) {  
            row = sheet.getRow(i);  
            int j = 0;  
            Map<Integer,Object> cellValue = new HashMap<Integer, Object>();  
            while (j < colNum) {  
                Object obj = getCellFormatValue(row.getCell(j));  
                cellValue.put(j, obj);  
                j++;  
            }  
            content.put(i, cellValue);  
        }  
        return content;  
    }  
    //给EXCEL文件里的内容进行排序，使其与数据库的内容一致
     public ArrayList<String>gettreeleaves(String nameoftree){ 		
    	 Sql sql=Sql.getInstance();   		
    	 ArrayList<String>datatitle=new ArrayList<String>();
    	 datatitle=sql.getLeaves(nameoftree);
    	 
    	 return datatitle;
     }
    public ArrayList<Object> equaldata(ArrayList<String>datatitle,String filepath) throws Exception{  
        if(wb==null){  
            throw new Exception("Workbook对象为空！");  
        }  
        sheet = wb.getSheetAt(0);  
        // 得到总行数  
        int rowNum = sheet.getLastRowNum();  //行数
        row = sheet.getRow(0);  
      
        
        ReadExcelUtils reader = ReadExcelUtils.getInstance();       
        Sql sql=Sql.getInstance(); 
        ArrayList<String>title=new ArrayList<String>();	
        HashSet<Object>hashset=new  HashSet<Object>();
        ArrayList<Object>content=new  ArrayList<Object>();
        ArrayList<Object>temp=new  ArrayList<Object>();
        ArrayList<Object>temp2=new  ArrayList<Object>();
        content=reader.readExcel();//文件内容
        title=reader.readExcelTitle();
     
   
        HashSet<String>excelname=new HashSet<String>();//文件内容
		HashSet<String>leavesname=new HashSet<String>();//叶子节点
		boolean datatest;
		for(int i=0;i<datatitle.size(); i++){
			Object obj=new Object();
			obj=datatitle.get(i);
			leavesname.add((String) obj);	
		}
		
		for(int i=0;i<title.size(); i++){
			Object obj=new Object();
			obj=title.get(i);
			excelname.add((String) obj);	
		}
		datatest=excelname.equals(leavesname);
		
		if(datatest){
			System.out.println("Data well done");
				
		}
		else
			System.out.println("Data wrong");	 //判断叶子节点个数是否相符
		
       if(datatest){
    	   System.out.println("进入循环");
    	   for(int i=0;i<datatitle.size();i++){
    		   if(hashset.contains(i))
  				i++;
    		 for(int j=0;j<title.size();j++){
    		
    			
    		
    			if( datatitle.get(i).equals(title.get(j))){
    				hashset.add(j);
    				hashset.add(i);
    				
    				for(int k=0;k<rowNum*2;k++){      				
    					   double numble;
    					   Object obj;
    					    obj=content.get(k);
    					    temp.add(obj);
    					    k++;   
    						double num[]=(double[]) content.get(k);    			
    						numble=num[i];
    						num[i]=num[j];
    						num[j]=numble;		
    					
    			    	   temp.add(num);
    				}
    			
    			}
    	
    		
    		 }
    				
    	   }
    	   
       }
       for (int i = 0; i < temp.size()/2; i++) { 
      
    	   Object obj;
    	   obj=temp.get(i);
       	temp2.add(obj);
       	i ++;
       	double [] num = (double[])temp.get(i);
       	temp2.add(num);
       }  
       return temp2;
    }  
    
	public ArrayList<Object> readExcel() throws Exception{  
        if(wb==null){  
            throw new Exception("Workbook对象为空！");  
        }  
        sheet = wb.getSheetAt(0);  
        // 得到总行数  
        int rowNum = sheet.getLastRowNum();  
        row = sheet.getRow(0);  
        int colNum = row.getPhysicalNumberOfCells();  
        // 正文内容应该从第二行开始,第一行为表头的标题  
        ArrayList <Object> result = new ArrayList<Object>();
        for (int i = 0; i < rowNum; i++) {  
            row = sheet.getRow(i+1);
            Object obj = getCellFormatValue(row.getCell(0));
            result.add(obj);
            int j = 1;  
            double num[] = new double[colNum-1];
            while (j < colNum) { 
                obj = getCellFormatValue(row.getCell(j));
                num[j-1] = Double.valueOf((String)obj);
                j++;  
            }   
            result.add(num);  
        }  
        return result;  
    }  
    
    @Test
    public void testread(){
    		try {  
        	ReadExcelUtils excelReader = ReadExcelUtils.getInstance();  
        	excelReader.setFilepath("F:\\1.xlsx");
            // 对读取Excel表格标题测试  
//          String[] title = excelReader.readExcelTitle();  
//          System.out.println("获得Excel表格的标题:");  
//          for (String s : title) {  
//              System.out.print(s + " ");  
//          }  
               
            // 对读取Excel表格内容测试  
            ArrayList<Object> result = excelReader.readExcel();
            System.out.println("获得Excel表格的内容:");  
            ArrayList<String> name = excelReader.readExcelTitle();
            for(int i= 0; i < name.size(); i++) { 
            	System.out.println(name.get(i));
            }
            for (int i = 0; i < result.size(); i++) { 
            	System.out.print(result.get(i)+"  ");
            	i ++;
            	double [] num = (double[])result.get(i);
            	for(int j = 0;j < num.length-1 ;j ++){
            		System.out.print(num[j]+"  ");  
            	}
            	System.out.println(num[num.length - 1]);
            }  
       } catch (FileNotFoundException e) {  
            System.out.println("未找到指定路径的文件!");  
            e.printStackTrace();  
        }catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
   /* @Test
    public void test2(){
    	System.out.println("This is test2");
		try {  
    	ReadExcelUtils excelReader = ReadExcelUtils.getInstance();  
    	excelReader.setFilepath("F:\\1.xlsx");
        // 对读取Excel表格标题测试  
//      String[] title = excelReader.readExcelTitle();  
//      System.out.println("获得Excel表格的标题:");  
//      for (String s : title) {  
//          System.out.print(s + " ");  
//      }  
        
    	// 获取数据库里的顺序
    	ArrayList<String> shunxu = new ArrayList<String>();   	
    	shunxu.add("n2");
    	shunxu.add("result");
    	shunxu.add("n1");  
    	System.out.println("shunxu:"+shunxu.toString());
        // 对读取Excel表格内容测试  
        ArrayList<Object> result = excelReader.equaldata(shunxu, filepath);      
        System.out.println("获得Excel表格的内容:");  
       // System.out.print(result.get()+"  ");
        for(int i= 0; i < shunxu.size(); i++) { 
        	System.out.println(shunxu.get(i));
        }
        for (int i = 0; i < result.size(); i++) { 
        	System.out.print(result.get(i)+"  ");
        	i ++;
        	double [] num = (double[])result.get(i);
        	for(int j = 0;j < num.length-1 ;j ++){
        		System.out.print(num[j]+"  ");  
        	}
        	System.out.println(num[num.length - 1]);
        }  
       //TODO 
   
   } catch (FileNotFoundException e) {  
        System.out.println("未找到指定路径的文件!");  
        e.printStackTrace();  
    }catch (Exception e) {  
        e.printStackTrace();  
    }  
	
}*/
    
    
    /** 
     *  
     * 根据Cell类型设置数据 
     *  
     * @param cell 
     * @return 
     * @author zengwendong 
     */  
    private Object getCellFormatValue(Cell cell) {  
        Object cellvalue = "";  
        if (cell != null) {  
            // 判断当前Cell的Type  
            switch (cell.getCellType()) {  
            case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC  
            case Cell.CELL_TYPE_FORMULA: {  
                // 判断当前的cell是否为Date  
                if (DateUtil.isCellDateFormatted(cell)) {  
                    // 如果是Date类型则，转化为Data格式  
                    // data格式是带时分秒的：2013-7-10 0:00:00  
                    // cellvalue = cell.getDateCellValue().toLocaleString();  
                    // data格式是不带带时分秒的：2013-7-10  
                    Date date = cell.getDateCellValue();  
                    cellvalue = date;  
                } else {// 如果是纯数字  
  
                    // 取得当前Cell的数值  
                    cellvalue = String.valueOf(cell.getNumericCellValue());  
                }  
                break;  
            }  
            case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING  
                // 取得当前的Cell字符串  
                cellvalue = cell.getRichStringCellValue().getString();  
                break;  
            default:// 默认的Cell值  
                cellvalue = "";  
            }  
        } else {  
            cellvalue = "";  
        }  
        return cellvalue;  
    }  
  
    public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
}  