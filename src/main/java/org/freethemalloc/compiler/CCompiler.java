/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.freethemalloc.compiler;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author Harshana
 */
public class CCompiler {

    private final String _FILE_NAME = "editorTemp.c";
    private String _TEMP_LOCATION;
    private File file = null;
    private JTextPane pane;

    public CCompiler(JTextPane pane) {
        _TEMP_LOCATION = System.getProperty("java.io.tmpdir")+ "Editor\\";
        this.pane = pane;
        pane.setText("");
    }
    
    public void execute(String code){
        if(createTempFile(code)){
            if(compile()){
                runFile();
            }
        }
    }

    /**
     * Create a file inside system temp folder
     *
     * @param code source code string
     * @return boolean
     */
    private boolean createTempFile(String code) {
        File dir = new File(_TEMP_LOCATION);
        if (!dir.exists()) {
            try {
                dir.mkdir();
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
        file = new File(_TEMP_LOCATION + _FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                return false;
            }
        }
        FileWriter fw;
        try {
            fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(code);
            bw.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(CCompiler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    private boolean compile() {
         
        boolean status = true;
        
        appendToPane(pane, "-----------Program is compiling------------ \n", Color.LIGHT_GRAY);
        Process process;
        try {
            process = Runtime.getRuntime().exec("gcc -Wall -o"+_TEMP_LOCATION+_FILE_NAME.split(".c")[0]+" "+_TEMP_LOCATION+_FILE_NAME);
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = reader1.readLine()) != null) {
               status = false;
                appendToPane(pane, line + "\n", null);
            }
            while ((line = reader2.readLine()) != null) {
                appendToPane(pane, line + "\n", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(status)
            appendToPane(pane, "----------- Compiling is done! ------------ \n", Color.LIGHT_GRAY);
        else
            appendToPane(pane, "----------- Compiling Error occured ! ------------ \n", Color.red);
       
        return status;
    }

    /**
     * Run the program
     */
    public void runFile() {

        appendToPane(pane, "----------- Program is executing ------------ \n", Color.LIGHT_GRAY);
        Process p;
        try {
            p = Runtime.getRuntime().exec(_TEMP_LOCATION + _FILE_NAME.split(".c")[0]);//exe file exist inside project root folder

            BufferedReader reader1 = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader1.readLine()) != null) {
                appendToPane(pane, line + "\n", null);
            }
            while ((line = reader2.readLine()) != null) {
                appendToPane(pane, line + "\n", null);
            }
            //p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        appendToPane(pane, "----------- executing is done ------------ \n", Color.LIGHT_GRAY);
    }

    private void appendToPane(JTextPane tp, String msg, Color c) {
        Color color;
        if (c == null) {
            color = Color.GREEN;
        } else {
            color = c;
        }

        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
}
