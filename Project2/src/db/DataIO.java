package db;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataIO<T> {

    public static String print(Table tbl) {
        // Stringbuilder
        StringBuilder sb = new StringBuilder();
        // print the definition
        for(int i=0; i<tbl.getColNum(); i++) {
            sb.append(tbl.getColList().get(i).getName()+" ");
            sb.append(tbl.getColList().get(i).getType());
            if (i<tbl.getColNum()-1)
                sb.append(",");
        }
        sb.append("\n");
        // print the content
        for(int i=0; i<tbl.getRowNum(); i++) {
            for(int j=0; j<tbl.getColNum(); j++) {
                sb.append(tbl.getRowList().get(i).getRowEList().get(j));
                if (j<tbl.getColNum()-1)
                    sb.append(",");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static Table load(String fileName) {
        // create local variable to store when reading
        String tblStr = null;
        List<String> tblStrList = new ArrayList<>();

        // read file
        File file = new File("/Users/ftl/"+fileName+".tbl");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));

            int line = 1;
            // read once for a line, until read to null
            while ((tblStr = reader.readLine()) != null) {
                // store line content into list
                tblStrList.add(tblStr);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        // Parse the file
        // Split the definition and content
        String tblDef = tblStrList.get(0);
        String[] tblRowStrArr = new String[tblStrList.size()-1];
        for (int i=0; i<tblStrList.size()-1; i++) {
            tblRowStrArr[i] = tblStrList.get(i+1);
        }

        // parse the definition
        String[] colDefArr = tblDef.split(",");
        String[] nameArr = new String[colDefArr.length];
        String[] typeArr = new String[colDefArr.length];
        for (int i=0; i<colDefArr.length; i++) {
            nameArr[i] = colDefArr[i].split(" ")[0];
            typeArr[i] = colDefArr[i].split(" ")[1];
        }
        // create new table
        Table tbl = new Table(fileName);
        // set column name and type
        Column col;
        for(int i=0; i<nameArr.length; i++) {
            col = new Column(nameArr[i], typeArr[i]);
            tbl.getColList().add(col);
            tbl.setColNum(tbl.getColNum()+1);
        }

        // parse the content
        for (String tblRowStr: tblRowStrArr) {
            String[] rowArr = tblRowStr.split(",");
            List<Object> rowEList = new ArrayList<>();
            for(int i=0; i<rowArr.length; i++) {
                // change to right type
                if (typeArr[i].equals("int"))
                    rowEList.add(Integer.parseInt(rowArr[i]));
                else if(typeArr[i].equals("float"))
                    rowEList.add(Float.parseFloat(rowArr[i]));
                else if(typeArr[i].equals("string"))
                    rowEList.add(rowArr[i]);
            }
            Row row = new Row(rowEList);
            // add row to table
            tbl.addRow(row);
        }

        return tbl;
    }

    private static boolean createTblFile(String tblName) throws IOException {
        boolean flag = false;
        String filenameTemp = "/Users/ftl/"+tblName+".txt";
        File filename = new File(filenameTemp);
        if (!filename.exists()) {
            filename.createNewFile();
            System.out.println("created");
            flag = true;
        }
        return flag;
    }

    public static void store(Table tbl) {
        FileOutputStream outStream = null;
        BufferedOutputStream Buff = null;
        try {
            //createTblFile(tbl.getName());
            outStream = new FileOutputStream(new File("/Users/ftl/"+tbl.getName()+".txt"));
            Buff = new BufferedOutputStream(outStream);
            long begin0 = System.currentTimeMillis();
            // print the definition
            for(int i=0; i<tbl.getColNum(); i++) {
                Buff.write(tbl.getColList().get(i).getName().getBytes());
                Buff.write(" ".getBytes());
                Buff.write(tbl.getColList().get(i).getType().getBytes());
                if (i<tbl.getColNum()-1)
                    Buff.write(",".getBytes());
            }
            Buff.write("\n".getBytes());
            // print the content
            for(int i=0; i<tbl.getRowNum(); i++) {
                for(int j=0; j<tbl.getColNum(); j++) {
                    Buff.write(tbl.getRowList().get(i).getRowEList().get(j).toString().getBytes());
                    if (j<tbl.getColNum()-1)
                        Buff.write(",".getBytes());
                }
                Buff.write("\n".getBytes());
            }

            Buff.flush();
            Buff.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            Buff.close();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            }
        }
    }

}
