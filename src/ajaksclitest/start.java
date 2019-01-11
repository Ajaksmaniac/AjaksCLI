/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajaksclitest;



import ajaksclitest.FileTree.Node;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileInputStream;


/**
 *
 * @author Ajaks
 * 
 * Aleksandar Jovanov 2017200014
 */


    

public class start {
    public static FileTree tree;
    public static Node CurrentDirectory = null;
    public static File file;
    public static File ROOTPATH = new File(System.getProperty("user.dir")+"//root");
    
    public static BufferedReader reader;
    public static LinkedList line = new LinkedList();
    
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
       
        System.out.println("****************AjaksCLI****************");
        System.out.println("LOADING FILE SYSTEM...");
        double startTime = System.currentTimeMillis();
        tree= new FileTree(ROOTPATH);
        CurrentDirectory =tree.ROOT;
        double endTime = System.currentTimeMillis();
        System.out.println("FINISHED IN " + (endTime - startTime) + " MS");
        Scanner sc = new Scanner(System.in);

       
        
    
       
      
        while(true){
           
             
            System.out.print(comandLine(CurrentDirectory)+">");
            String rawCommand = sc.nextLine();
            String[] tokens = rawCommand.split(" ");
   
            switch(tokens[0]){
                case "cd":
                    String cd = tokens[1];
                    if(cd.equals("..")){
                        if(CurrentDirectory.data.getName().equals("root")){
                            System.out.println("No permisison to move out of root folder");
                            CurrentDirectory = tree.ROOT;
                        }else{
                            CurrentDirectory = tree.getParrent(CurrentDirectory);
                           
                        }
                            
                    }
     
                    CurrentDirectory = tree.searchDir(CurrentDirectory, cd);
                    
                    break;                 
                
                case "exit":
                    exit();
                    break;
                case "search":
                         
                    String search = tokens[1];
                    Node a = tree.searchTree(search);
                        if(a == null){
                            System.out.println("Not found!");
                        }
                    break;
                    
                case "":
                    System.out.println();
                    break;
                    
                case "dir":
                    tree.showDir(CurrentDirectory);
                    
                    break;
                    
                case "pcd":
                    System.out.println(tree.printCurrentDirectoryPath(CurrentDirectory));
                    break;
                    
                case "refresh":
                        
                    tree.refreshTree();
                    CurrentDirectory = tree.ROOT;
                    break;
                    
                case "mkdir":
                    String makeDirectoryName =  tokens[1];
                    System.out.print(makeDirectoryName + "\n");
                    tree.makeDirectory(CurrentDirectory, makeDirectoryName);
        
                    break;
      
                case "rm":
                    String removeDirectoryName =  tokens[1];
                    System.out.print(removeDirectoryName + "\n");
                    tree.removeDirectory(CurrentDirectory, removeDirectoryName);
                        
                       
                    break
                            ;
                case "rmdir":
                    String removeEmptyDirectoryName =  tokens[1];
                    System.out.print(removeEmptyDirectoryName + "\n");
                    tree.removeEmptyDirectory(CurrentDirectory, removeEmptyDirectoryName);
                    break;
                    
                case "rename":
                    String renameDir =  tokens[1];
                    String renameDirTo =  tokens[2];
                       
                    tree.renameDirectory(CurrentDirectory, renameDir, renameDirTo);

                    break;
                    
                    
             /* case "copy":
                    String source  =  tokens[1];
                    String dest =  tokens[2];
                        
                       
                    File sourceLocation= new File(source);
                    File targetLocation = new File(dest);

                    tree.copyFolder(sourceLocation, targetLocation);

                    break;*/
                case "move":
                    String file  =  tokens[1];
                    String loc =  tokens[2];
                    tree.moveDirectory(CurrentDirectory, file, loc);
                        
                    break;
                    
                case "open":
                    String filer  =  tokens[1];
                    Node textFile  = tree.searchDir(CurrentDirectory, filer);
                    if(textFile == null){
                        System.out.println("File not found");
                    }else{
                              
                        if(textFile.data.getName().endsWith(".txt")){
                            Scanner in = new Scanner(new FileReader(textFile.data));
                            while(in.hasNextLine()){
                                   
                                System.out.println(in.nextLine());
                            }
                                 
                        }else{
                            System.out.println(textFile.data.getName() + " is a directory, not a file!");
                            }
                                    
                          }
                    break;
                    
                case "m":
                    Node commandList = tree.searchDir(tree.ROOT, "List_of_commands.txt");
                    System.out.println(commandList.data.getName());
                   
                    Scanner in = new Scanner(new FileReader(commandList.data));

                    while(in.hasNextLine()){
                               
                        System.out.println(in.nextLine());
                    }
                    
                    
                    break;
                    
                case "hex":
                    String hex  = tokens[1];
                    Node fileToHex = tree.searchDir(CurrentDirectory, hex);
                    String[] bytes;
                    byte[] fileContent = Files.readAllBytes(fileToHex.data.toPath());
                         
                    StringBuilder sbh = new StringBuilder();
                            
                    for (byte b : fileContent) {
                        sbh.append(String.format("%02X ", b));
                        sbh.append(Integer.toHexString(b));
                                
                    }
                         
                         
                    System.out.println(Arrays.toString(fileContent));
                    break;
                    
                default:
                    System.out.print("Unknown Command type 'm' for list of commands\n");
                    break;
            }
           
             tree.refreshTree();
           
            
        }
        
 
    }
    //command line
    public static  String comandLine(Node Curent){
        String string = Curent.data.getName();
        LinkedList line = new LinkedList();
        line.prepend("\\"+string);
        while(Curent.parrent != null){
            Curent = Curent.parrent;
            string =  Curent.data.getName() ;
            line.prepend("\\"+string);
        }
            
        return line.toString();
    }

    //stops the program
    public static void exit(){
        System.exit(0);
    }
    
  
 

   
}


