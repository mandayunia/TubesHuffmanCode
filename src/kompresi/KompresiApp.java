/*
 * KompresiApp.java
 */
package kompresi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import kompresi.huffman.DekompresFile;
import kompresi.huffman.KompresiFile;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class KompresiApp extends SingleFrameApplication {

    private File inputFile;
    private File outputFile;

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        show(new KompresiView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of KompresiApp
     */
    public static KompresiApp getApplication() {
        return Application.getInstance(KompresiApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(KompresiApp.class, args);
    }

    /**
     * @return the inputFile
     */
    public File getInputFile() {
        return inputFile;
    }

    /**
     * @param inputFile the inputFile to set
     */
    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    /**
     * @return the outputFile
     */
    public File getOutputFile() {
        return outputFile;
    }

    /**
     * @param outputFile the outputFile to set
     */
    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public boolean doCompression() throws FileNotFoundException, IOException {
        KompresiFile.beginKompress(inputFile.getAbsolutePath(), inputFile.getParentFile().getAbsolutePath() + "\\" + inputFile.getName() + ".bin");
        return true;
    }

    public boolean doDecompression() throws IOException {
        DekompresFile.begindekompress(inputFile.getAbsolutePath(), inputFile.getParentFile().getAbsolutePath() + "\\" + removeExtention(inputFile.getName()));
        return true;
    }

    public static String removeExtention(String filePath) {
        // These first few lines the same as Justin's
        File f = new File(filePath);

        // if it's a directory, don't remove the extention
        if (f.isDirectory()) {
            return filePath;
        }

        String name = f.getName();

        // Now we know it's a file - don't need to do any special hidden
        // checking or contains() checking because of:
        final int lastPeriodPos = name.lastIndexOf('.');
        if (lastPeriodPos <= 0) {
            // No period after first character - return name as it was passed in
            return filePath;
        } else {
            // Remove the last period and everything after it
            File renamed = new File(f.getParent(), name.substring(0, lastPeriodPos));
            return renamed.getPath();
        }
    }
}
