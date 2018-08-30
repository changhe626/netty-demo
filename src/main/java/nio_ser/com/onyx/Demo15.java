package nio_ser.com.onyx;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author zk
 * @Description:
 * @date 2018-07-23 14:50
 */
public class Demo15 {

    /**
     * Files.exists()方法检查给定的Path在文件系统中是否存在。

     可以创建在文件系统中不存在的Path实例。例如，如果您计划创建一个新目录，您首先要创建相应的Path实例，然后创建目录。

     由于Path实例可能指向，也可能没有指向文件系统中存在的路径，你可以使用Files.exists()方法来确定它们是否存在(如果需要检查的话)。

     这里是一个Java Files.exists()的例子：

     */
    @Test
    public void test1(){
        String path="F:\\API\\DTD.chm";
        Path path1 = Paths.get(path);

        boolean exists = Files.exists(path1, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
        System.out.println(exists);
    }


    /**
     * Files.createDirectory()方法，用于根据Path实例创建一个新目录
     *
     * 第一行创建表示要创建的目录的Path实例。在try-catch块中，用路径作为参数调用Files.createDirectory()方法。如果创建目录成功，
     * 将返回一个Path实例，该实例指向新创建的路径。

     如果该目录已经存在，则是抛出一个java.nio.file.FileAlreadyExistsException。如果出现其他错误，可能会抛出IOException。
     例如，如果想要的新目录的父目录不存在，则可能会抛出IOException。父目录是您想要创建新目录的目录。因此，它表示新目录的父目录。

     */
    @Test
    public void test2(){
        String path="F:\\API\\测试";
        Path path1 = Paths.get(path);
        try {
            Path path2 = Files.createDirectories(path1);
            System.out.println(path2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Files.copy()方法从一个路径拷贝一个文件到另外一个目录
     * 首先，该示例创建一个源和目标Path实例。然后，这个例子调用Files.copy()，将两个Path实例作为参数传递。
     * 这可以让源路径引用的文件被复制到目标路径引用的文件中。

     如果目标文件已经存在，则抛出一个java.nio.file.FileAlreadyExistsException异常。如果有其他错误，则会抛出一个IOException。
     例如，如果将该文件复制到不存在的目录，则会抛出IOException。

     请注意Files.copy()方法的第三个参数。如果目标文件已经存在，这个参数指示copy()方法覆盖现有的文件

     */
    @Test
    public void test3(){
        String path="F:\\API\\DTD.chm";
        Path path1 = Paths.get(path);
        try {
            Path copy = Files.copy(path1, Paths.get("F:\\API\\DTD2.chm"), StandardCopyOption.REPLACE_EXISTING);
            System.out.println(copy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * Java NIO Files还包含一个函数，用于将文件从一个路径移动到另一个路径。移动文件与重命名相同，
     * 但是移动文件既可以移动到不同的目录，也可以在相同的操作中更改它的名称。
     * 是的,java.io.File类也可以使用它的renameTo()方法来完成这个操作，但是现在已经在java.nio.file.Files中有了文件移动功能。
     *
     * 首先创建源路径和目标路径。源路径指向要移动的文件，而目标路径指向文件应该移动到的位置。然后调用Files.move()方法。这会导致文件被移动。

     请注意传递给Files.move()的第三个参数。这个参数告诉Files.move()方法来覆盖目标路径上的任何现有文件。这个参数实际上是可选的。

     如果移动文件失败，Files.move()方法可能抛出一个IOException。例如，如果一个文件已经存在于目标路径中，并且您已经排除了StandardCopyOption.REPLACE_EXISTING选项，或者被移动的文件不存在等等。

     */
    @Test
    public void test4(){
        String path="F:\\API\\DTD.chm";
        Path path1 = Paths.get(path);
        try {
            Path path2 = Files.move(path1, Paths.get("F:\\API2\\DTD2.chm"), StandardCopyOption.REPLACE_EXISTING);
            System.out.println(path2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Files.delete()方法可以删除一个文件或者目录
     *
     * 首先，创建指向要删除的文件的Path。然后调用Files.delete()方法。
     * 如果Files.delete()由于某种原因不能删除文件(例如，文件或目录不存在)，会抛出一个IOException。
     */
    @Test
    public void test5(){
        String path="F:\\API2\\DTD2.chm";
        Path path1 = Paths.get(path);
        try {
            Files.delete(path1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Files.walkFileTree()方法包含递归遍历目录树的功能。walkFileTree()方法将Path实例和FileVisitor作为参数。
     * Path实例指向您想要遍历的目录。FileVisitor在遍历期间被调用。

     在我解释遍历是如何工作之前，这里我们先了解FileVisitor接口:
     public interface FileVisitor {
     public FileVisitResult preVisitDirectory(
     Path dir, BasicFileAttributes attrs) throws IOException;

     public FileVisitResult visitFile(
     Path file, BasicFileAttributes attrs) throws IOException;

     public FileVisitResult visitFileFailed(
     Path file, IOException exc) throws IOException;

     public FileVisitResult postVisitDirectory(
     Path dir, IOException exc) throws IOException {
     }

     您必须自己实现FileVisitor接口，并将实现的实例传递给walkFileTree()方法。在目录遍历过程中，
     您的FileVisitor实现的每个方法都将被调用。如果不需要实现所有这些方法，那么可以扩展SimpleFileVisitor类，
     它包含FileVisitor接口中所有方法的默认实现。


     FileVisitor实现中的每个方法在遍历过程中的不同时间都被调用:

     在访问任何目录之前调用preVisitDirectory()方法。在访问一个目录之后调用postVisitDirectory()方法。

     调用visitFile()在文件遍历过程中访问的每一个文件。它不会访问目录-只会访问文件。在访问文件失败时调用visitFileFailed()方法。例如，如果您没有正确的权限，或者其他什么地方出错了。

     这四个方法中的每个都返回一个FileVisitResult枚举实例。FileVisitResult枚举包含以下四个选项:

     CONTINUE 继续
     TERMINATE 终止
     SKIP_SIBLING 跳过同级
     SKIP_SUBTREE 跳过子级
     通过返回其中一个值，调用方法可以决定如何继续执行文件。

     CONTINUE继续意味着文件的执行应该像正常一样继续。

     TERMINATE终止意味着文件遍历现在应该终止。

     SKIP_SIBLINGS跳过同级意味着文件遍历应该继续，但不需要访问该文件或目录的任何同级。

     SKIP_SUBTREE跳过子级意味着文件遍历应该继续，但是不需要访问这个目录中的子目录。这个值只有从preVisitDirectory()返回时才是一个函数。如果从任何其他方法返回，它将被解释为一个CONTINUE继续。

     */
    @Test
    public void test6() throws IOException {
        String path="D:\\360Downloads";
        Path path1 = Paths.get(path);
        Files.walkFileTree(path1, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("pre visit dir:" + dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("visit file: " + file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.out.println("visit file failed: " + file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("post visit directory: " + dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }


    /**
     * 这里是一个用于扩展SimpleFileVisitor的walkFileTree()，以查找一个名为README.txt的文件:
     */
    @Test
    public void test7() {
        Path rootPath = Paths.get("data");
        final String fileToFind = File.separator + "README.txt";

        try {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String fileString = file.toAbsolutePath().toString();
                    System.out.println("pathString = " + fileString);

                    if(fileString.endsWith(fileToFind)){
                        System.out.println("file found at path: " + file.toAbsolutePath());
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch(IOException e){
            e.printStackTrace();
        }

    }


    /**
     *Files.walkFileTree()也可以用来删除包含所有文件和子目录的目录。
     * Files.delete()方法只会删除一个目录，如果它是空的。
     * 通过遍历所有目录并删除每个目录中的所有文件(在visitFile())中，然后删除目录本身(在postVisitDirectory()中)，
     * 您可以删除包含所有子目录和文件的目录。下面是一个递归目录删除示例:
     */
    @Test
    public void test8() {
        Path rootPath = Paths.get("data/to-delete");
        try {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println("delete file: " + file.toString());
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    System.out.println("delete dir: " + dir.toString());
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
