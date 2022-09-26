package edu.jsu.mcis.cs310;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
        
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and
        other whitespace have been added for clarity).  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings and which values should be encoded as integers, as
        well as the overall structure of the data!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160",
            "111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
        
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
        
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and JSON.simple.  See the "Data
        Exchange" lecture notes for more details, including examples.
        
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            // Initialize CSV Reader and Iterator
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
            /* INSERT YOUR CODE HERE */
          
            JSONObject JSONobj = new JSONObject();
            
          
            
            JSONArray colh = new JSONArray(); 
            JSONArray rowh = new JSONArray();
            JSONArray datah = new JSONArray();
            
            String[] col = iterator.next();
            for(String s : col){
                colh.add(s);
            }
            
            while (iterator.hasNext()) {                    
                String[] row = iterator.next();                   
                rowh.add(row[0]);
                JSONArray data = new JSONArray();
                for (int i = 1; i < row.length; ++i) { 
                    data.add(Integer.parseInt(row[i]));
                }
                datah.add(data);
            
            }
            JSONobj.put("rowheaders", rowh);
            JSONobj.put("data", datah);
            JSONobj.put("colheaders", colh);
            
            results = JSONValue.toJSONString(JSONobj);
            reader.close();
            
            
        }
        catch(Exception e) { e.printStackTrace(); }
        
        // Return JSON String
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {
            
            // Initialize JSON Parser and CSV Writer
            
            JSONParser parser = new JSONParser();
            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\\', "\n");
            
            /* INSERT YOUR CODE HERE */
            
            
            
            JSONObject json = (JSONObject)parser.parse(jsonString); 
            
           
            JSONArray col = (JSONArray)json.get("colheaders");
            JSONArray rowh = (JSONArray)json.get("rowheaders");
            JSONArray datah = (JSONArray)json.get("dataheaders");
            
            String[] colString = new String[col.size()];
            String[] rowString = new String[rowh.size()];
            String[] dataString = new String[datah.size()];
                    
     
            for(int i = 0; i < col.size(); i++){
   
                
                colString[i] = col.get(i).toString();
                
            }
            csvWriter.writeNext(colString);
            
            for(int i = 0; i < rowh.size(); i++){
                
                rowString[i] = rowh.get(i).toString();
                dataString[i] = datah.get(i).toString();
            } 
            for(int i = 0; i < dataString.length; i++) {
                JSONArray dataparse= (JSONArray)parser.parse(dataString[i]);
                String[] rowelements = new String[dataparse.size() + 1];
                
                
                for(int j = 0; j < rowelements.length; j++) {
                    rowelements[i] = dataparse.get(i).toString();
                    
                }
       
                csvWriter.writeNext(rowelements);
                
                results = writer.toString();
                
                
            
            }
                 
        }
        catch(Exception e) { e.printStackTrace(); }
        
        // Return CSV String
        
        return results.trim();
        
    }
	
}