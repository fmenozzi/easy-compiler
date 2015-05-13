/**
 * Easy Compiler
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1 
 */

package easy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import easy.ErrorReporter;

import easy.SyntacticAnalyzer.Parser;
import easy.SyntacticAnalyzer.Scanner;

import easy.AbstractSyntaxTrees.AST;

import easy.CodeGenerator.Generator;

public class easyc {
	
	static ErrorReporter reporter;

	public static void main(String[] args) {
				
		InputStream inputStream = null;
		if (args.length != 1) {
			System.out.println("Usage: java -jar path/to/easyc.java path/to/source.easy");
			System.exit(0);
		} else {
			try {
				inputStream = new FileInputStream(args[0]);
			} catch (FileNotFoundException e) {
				System.out.println("Input file " + args[0] + " not found");
				System.exit(1);
			}		
		}
		
		reporter = new ErrorReporter();
		Scanner scanner = new Scanner(inputStream, reporter);

		AST ast = new Parser(scanner, reporter).parse();
		
		checkForErrors();
		File outputJavaFile = generateJavaSource(args[0], ast);
		compileJavaSource(outputJavaFile);
		printSuccessMessage();			
	}
	
	private static void checkForErrors() {
		if (reporter.hasErrors()) {
			reporter.reportErrors();
			System.exit(4);
		}
	}
	
	private static File generateJavaSource(String fileName, AST ast) {
		int easyIndex = fileName.indexOf(".ez");
		if (easyIndex == -1) {
				System.err.println("File must end in \".ez\"");
				System.exit(4);
		}
		
		String sourceFileNameBeforeDot = fileName.substring(0, easyIndex);
		File outputJavaFile = new File(sourceFileNameBeforeDot + ".java");
		new Generator(outputJavaFile).generate(ast);
		
		return outputJavaFile;
	}
	
	private static void compileJavaSource(File sourceFile) {
		try {
			Runtime.getRuntime().exec("javac " + sourceFile.getAbsolutePath());
		} catch (IOException e) {
			System.err.println("Unable to invoke Java compiler javac");
			e.printStackTrace();
			System.exit(4);
		}
	}
	
	private static void printSuccessMessage() {
		System.out.println();
		System.out.println(" _____  _   _  _____  _____    _    _  _____  _____    _____  _____  _____  _   _  _ ");
		System.out.println("(_   _)( )_( )(  _  )(_   _)  ( )  ( )(  _  )(  ___)  (  ___)(  _  )(  ___)( )_( )| |");
		System.out.println("  ( )  (  _  )( (_) )  ( )    ( )()( )( (_) )(___  )  (  ___)( (_) )(___  ) (   ) |_|");
		System.out.println("  (_)  (_) (_)(_) (_)  (_)     (_)(_) (_) (_)(_____)  (_____)(_) (_)(_____)  (_)  (_)");
		System.out.println();	
	}
}
