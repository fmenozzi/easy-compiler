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
import java.io.InputStream;

import easy.ErrorReporter;

import easy.SyntacticAnalyzer.Parser;
import easy.SyntacticAnalyzer.Scanner;

import easy.AbstractSyntaxTrees.AST;

import easy.CodeGenerator.Generator;

public class easyc {

	public static void main(String[] args) {
				
		InputStream inputStream = null;
		if (args.length != 1) {
			System.out.println("Usage: java -jar path/to/easyc.java path/to/source.easy");
			System.exit(1);
		} else {
			try {
				inputStream = new FileInputStream(args[0]);
			} catch (FileNotFoundException e) {
				System.out.println("Input file " + args[0] + " not found");
				System.exit(1);
			}		
		}
		
		ErrorReporter reporter	= new ErrorReporter();
		Scanner scanner 		= new Scanner(inputStream, reporter);

		AST ast = new Parser(scanner, reporter).parse();
		
		if (reporter.hasErrors()) {
			reporter.reportErrors();
			System.exit(4);
		} else {						
			String sourceFileNameBeforeDot = args[0].substring(0, args[0].indexOf("."));
			new Generator(new File(sourceFileNameBeforeDot + ".java")).generate(ast);
		}
	}
	
}
