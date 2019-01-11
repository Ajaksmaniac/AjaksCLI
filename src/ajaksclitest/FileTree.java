/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajaksclitest;

import java.io.File;

import java.util.ArrayList;


/**
 *
 * @author Ajaks
 */
public class FileTree {
    private LinkedList<Node> queue = new LinkedList<>();
    public class Node {
      
        ArrayList<Node> sons = new ArrayList<>();
        public File data;
        public Node parrent;

        public Node(File data, Node parrent ) {
            this.data = data;
            this.parrent = parrent;
            
        }
        public Node(File data) {
            this.data = data;
           
        }

   
    
    }
    public Node ROOT;
    
    public FileTree(File rootFile){
        ROOT = new Node(rootFile,null);
        MakeTree();
        
    }
    

   
        //Kreira stablo direktorijuma koje se poziva u konstruktoru FileTree klase
     
   void MakeTree(){
       
        enqueue(ROOT);
        //listDir(ROOT);
        Node curNode = ROOT;
        while(!queue.isEmpty()){
            curNode = dequeue();
            //listDir(curNode);
            
            for(File f: curNode.data.listFiles()){
              
                Node toAdd = new Node(f,curNode);
                curNode.sons.add(toAdd);
                
            if(f.isDirectory()){
                
                    enqueue(toAdd);
                }
          //System.out.println(dir.data.getName()); 
          
        } 
            /*for(Node n : curNode.sons){
                if(n.data.isDirectory()){
                    enqueue(n);
                }
            }*/
            
            //System.out.println(queue.toString()); 
            
            
         /*  for(File f : curNode.data.listFiles()){
               
               Node toAdd = new Node(f,curNode);
               ROOT.sons.add(toAdd);
               if(f.isDirectory()){
                   enqueue(toAdd);
                     listDir(toAdd);
               }
               dequeue();
               
                
               
             
           }*/
           
         
    }
    
    
    }
   
   void delistTree(){
       this.ROOT.sons = new ArrayList<>();
       
        enqueue(ROOT);
        
        Node curNode = ROOT;
        while(!queue.isEmpty()){
          curNode = dequeue();
           
            
            for(Node n: curNode.sons){
                n.sons = new ArrayList<>();
                
                
                if(n.data.isDirectory()){
                
                enqueue(n);
                }
            } 
        }
    }
   
   void refreshTree(){
        delistTree();
       
        enqueue(ROOT);
        
        Node curNode = ROOT;
        while(!queue.isEmpty()){
          curNode = dequeue();
           
            
            for(File f: curNode.data.listFiles()){
                Node toAdd = new Node(f,curNode);
                
                curNode.sons.add(toAdd);
                if(f.isDirectory()){
                
                    enqueue(toAdd);
                }
                
            
         
          
        } 
            
           
         
    }
    
    
    }
      //prelistava direktorijum i dodaje sinove trenutnog direktorijuma
    public void listDir(Node dir){
        /*if(emptyDir(dir.data)){
             //System.out.println("Empty"); 
         }*/
        
        for(File f: dir.data.listFiles()){
          
            dir.sons.add(new Node(f,dir));
          //System.out.println(dir.data.getName()); 
          
        } 
         
    }
    
    
    public void refresh(){
         MakeTree();
    }
    public boolean emptyDir(File f){
        return f.list().length < 0;
    }
    
    public boolean isEmpty(){
        if(queue.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    public void enqueue(Node data) {
        queue.append(data);
    }

    public Node dequeue() {
        return queue.removeFirst();
    }

    public Node peek() {
        return queue.getFirstData();
    }
    
    @Override
    public String toString() {
        return queue.toString();
    }
    
     
     
    public void showDir(Node dir){
         
         for(Node n : dir.sons)
       {
            System.out.println(n.data.getName());
        }
        System.out.println();
        //System.out.println(dir.sons.toString());
         
    }
    
    //Pretrazuje direkotorijum
     public Node searchDir(Node dir, String s){
       
        for(Node n: dir.sons){
           if(n.data.getName().equals(s)){
               
                return n;
             }
           }
            
            return dir;
         }
     
     
    //pretrazuje stablo
      public Node searchTree(String s){
        enqueue(ROOT);
        //listDir(ROOT);
        Node curNode ;
        while(!queue.isEmpty()){
           curNode = dequeue();
            //listDir(curNode);
            
            for(Node n: curNode.sons){
                
                if(n.data.isDirectory()){
                
                    enqueue(n);
                }
                
                if(n.data.getName().equals(s)){
                    
                    return n;
                }
                
            
          //System.out.println(dir.data.getName()); 
          
             } 
             
            
        }
        return null;
      }
            
        
          
    
    
     public String printCurrentDirectoryPath(Node node){
         return node.data.getAbsolutePath();
     }
     
     
     
     public void makeDirectory(Node currNode,String dirName){
         File file = new File(currNode.data.getAbsolutePath()+"\\"+dirName);
         file.mkdir();
         
         currNode.sons.add(new Node(file,currNode));
        
     }
     public void removeDirectory(Node currNode,String dirName){
        Node rmdir = searchDir(currNode,dirName);
         
         
         
         File file = new File(currNode.data.getAbsolutePath()+"\\"+dirName);
         file.delete();
         
         currNode.sons.remove(rmdir);
     }
     
     
     
     public void removeEmptyDirectory(Node currNode, String emptyDirToRemove){
         Node rmdir = searchDir(currNode,emptyDirToRemove);
         
         if(rmdir.data.list().length <0){
             File file = new File(currNode.data.getAbsolutePath()+"\\"+emptyDirToRemove);
             file.delete();
         }else{
             System.out.print("Folder is not empty");
         }
     }
     //Rename directory, takes current node, and new directory name
     public void renameDirectory(Node currNode,String dirName,String renameDirTo){
        Node renameDir = searchDir(currNode,dirName);
        if(renameDir != null){
            /*File file = new File(currNode.data.getAbsolutePath() + "\\"+dirName);
            File file2 = new File(currNode.data.getAbsolutePath() + "\\"+renameDirTo);
            file.renameTo(file2);*/
            renameDir.data.renameTo(new File(currNode.data.getPath()+"\\"+renameDirTo));
       
        }
        
     }
     
     
     public void moveDirectory(Node currNode,String dirName,String dirDestination ){
        Node movingDirNode = searchTree(dirName);
        Node dirDestinationNode = searchTree(dirDestination);
        if(movingDirNode != null ){
            if(dirDestination.equals("..")){
                String parrent = currNode.parrent.data.getPath();
                
                movingDirNode.data.renameTo(new File(parrent+"\\"+dirName)); 
            }else if(dirDestinationNode != null){
                movingDirNode.data.renameTo(new File(dirDestinationNode.data.getPath() +/*"\\"+ dirDestination +*/"\\" + dirName)); 
            }else{
                System.out.print("Destination file not found");
            }
        }else{
            System.out.print("No such file in the directory");
        }
       /* if(movingDirNode != null){
            if(dirDestination.equals("..")){
                String parrent = currNode.parrent.data.getPath();
                
                movingDirNode.data.renameTo(new File(parrent+"\\"+dirName)); 
            }else{
                movingDirNode.data.renameTo(new File(currNode.data.getAbsolutePath() +"\\"+ dirDestination + "\\" + dirName)); 
            }
            
        }else{
           System.out.print("No such file in the directory");
        }*/
        
     }
     
     //Gets parrent of the current Node
     public Node getParrent(Node n){
         if(n == ROOT){
             return null;
         }else{
            Node parrent = n.parrent;
            return parrent;
         }
         
     }
             
     // NE RADI!
     
      /*public static void copyFolder(File sourceFolder, File destinationFolder) throws IOException
    {
        
        //Check if sourceFolder is a directory or file
        //If sourceFolder is file; then copy the file directly to new location
        if (sourceFolder.isDirectory())
        {
            //Verify if destinationFolder is already present; If not then create it
            if (!destinationFolder.exists())
            {
                destinationFolder.mkdir();
                System.out.println("Directory created :: " + destinationFolder);
            }
             
            //Get all files from source directory
            String files[] = sourceFolder.list();
             
            //Iterate over all files and copy them to destinationFolder one by one
            for (String file : files)
            {
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);
                 
                //Recursive function call
                copyFolder(srcFile, destFile);
            }
        }
        else
        {
            //Copy the file content from one place to another
            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied :: " + destinationFolder);
        }
    }*/
         
}
