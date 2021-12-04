package ucf.assignments;
import com.opencsv.CSVWriter;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class todo {
    int id;
    String title;
    String desc;
    String status;
    Date todaoDate;

    public todo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTodoDate() {
        return todaoDate;
    }

    public void setTodoDate(Date todaoDate) {
        this.todaoDate = todaoDate;
    }

    public todo(int id, String title, String desc, String status, Date todaoDate) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.status = status;
        this.todaoDate = todaoDate;
    }

    public boolean createTodo(todo tod) {
        String csv = "TodoList.csv";
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(csv, true));
            String todo = tod.getId()+","+tod.getTitle()+","+tod.getDesc()+","+tod.getStatus()+","+tod.getTodoDate();
            String [] record = todo.split(",");
            writer.writeNext(record);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<todo> readFileTodoList(String fileName) throws IOException {
        if (fileName.isEmpty()){
            fileName = "TodoList.csv";
        }
        List<todo> result = new ArrayList<todo>();
        BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
        try {
            // Read first line
            String line = br.readLine();
            // Make sure file has correct headers
            if (line==null) throw new IllegalArgumentException("File is empty");

            // Run through following lines
            while ((line = br.readLine()) != null) {
                // Break line into entries using comma
                String[] items = line.split(",");
                try {
                    // If there are too many entries, throw a dummy exception, if
                    // there are too few, the same exception will be thrown later
                    if (items.length>4) throw new ArrayIndexOutOfBoundsException();
                    // Convert data to person record
                    todo tod = new todo();
                    tod.setId(Integer.valueOf(removeFirstandLast(items[0])));
                    tod.setTitle (removeFirstandLast(items[1]));
                    tod.setDesc(removeFirstandLast(items[2]));
                    tod.setStatus(removeFirstandLast(items[3]));
                    String sDate1=removeFirstandLast(items[4]);
                    Date date1=new SimpleDateFormat("YYYY-MM-DD").parse(sDate1);
                    tod.setTodoDate(date1);
                    result.add(tod);
                } catch (ArrayIndexOutOfBoundsException | NumberFormatException | NullPointerException | ParseException e) {
                    // Caught errors indicate a problem with data format -> Print warning and continue
                    System.out.println("Invalid line: "+ line);
                }
            }
            return result;
        } finally {
            br.close();
        }
    }


    public String removeFirstandLast(String str)
    {

        // Removing first and last character
        // of a string using substring() method
        str = str.substring(1, str.length() - 1);

        // Return the modified string
        return str;
    }



}
