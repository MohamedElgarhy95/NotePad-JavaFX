/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package mynotepad;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Mohamed Elgarhy
 */
public class MyNotepad extends Application {
    MenuBar m;
    Menu file,edit,help;
    BorderPane root;
    TextArea t;
    MenuItem New,open,save,exit,undo,cut,copy,paste,del,selectAll,about;
    Scene scene;
    Dialog<String> dialog;
    
   @Override
    public void init(){
        New = new MenuItem("New");
        open = new MenuItem("Open");
        save = new MenuItem("Save");
        exit = new MenuItem("Exit");
        undo = new MenuItem("Undo");
        cut = new MenuItem("Cut");
        copy = new MenuItem("Copy");
        paste = new MenuItem("Paste");
        del = new MenuItem("Delete");
        selectAll = new MenuItem("Select All");
        about = new MenuItem("Apout Notepad");
        
        file = new Menu("File");
        edit = new Menu("Edit");
        help = new Menu("Help");
        
        m = new MenuBar();
        
        root = new BorderPane();
        
        t = new TextArea();
        
        file.getItems().addAll(New,open,save,new SeparatorMenuItem(),exit);
        edit.getItems().addAll(undo,new SeparatorMenuItem(),cut,copy,paste,del,new SeparatorMenuItem(),selectAll);
        help.getItems().addAll(about);
        
        m.getMenus().addAll(file,edit,help);
        
        root.setTop(m);
        root.setCenter(t);
      
        
        scene = new Scene(root, 500, 500);
        
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        addItemEvents(primaryStage);
        
        primaryStage.setTitle("JavaFX NotePad");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void addItemEvents(Stage stage){
        dialog = new Dialog<String>();
        ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        
        New.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
                t.clear();
            }
        });
        
        open.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
                FileChooser fil_chooser = new FileChooser();
                File file = fil_chooser.showOpenDialog(stage);
                if (file != null) {
                    try {
                        FileInputStream in = new FileInputStream(file);
                        int count = in.available();  
                        byte[] ary = new byte[count];  
                        in.read(ary);
                        t.setText(new String(ary));
                        in.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MyNotepad.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MyNotepad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        });
        
        save.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
                dialog.setTitle("save");
                DirectoryChooser dir_chooser = new DirectoryChooser();
                File file = dir_chooser.showDialog(stage); 
                if (file != null) {
                    dialog.setContentText(file.getAbsolutePath());
                    TextInputDialog td = new TextInputDialog();
                    td.setHeaderText("enter file name");
                    td.showAndWait();
                    try {
                        file = new File(file.getAbsolutePath()+"/"+td.getEditor().getText());
                        if(file.createNewFile()){
                            FileOutputStream ou = new FileOutputStream(file);
                            byte[] strToBytes = t.getText().getBytes();
                            ou.write(strToBytes);
                            ou.close();
                        }
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MyNotepad.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MyNotepad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        exit.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
        
        undo.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
                t.undo();
            }
        });
        
        cut.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
                t.cut();
            }
        });
        
        copy.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
                t.copy();
            }
        });
        
        paste.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
                t.paste();
            }
        });
        
        del.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
                t.deleteText(t.getSelection());
            }
        });
        
        selectAll.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
                t.selectAll();
            }
        });
        
        about.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
                dialog.setTitle("About");
                dialog.setContentText("Welcome to my notepad");
                dialog.showAndWait();
            }
        });
    }
    /**
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    MenuItem fun (EventHandler<ActionEvent> ev){
        MenuItem m = new MenuItem("my NotePad");
        m.setOnAction(ev);    
        return m;
    }
    
}
