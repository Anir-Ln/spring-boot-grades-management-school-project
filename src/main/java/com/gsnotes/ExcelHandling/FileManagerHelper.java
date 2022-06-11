package com.gsnotes.ExcelHandling;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Tarik BOUDAA
 * 
 */
public class FileManagerHelper {

	/**
	 * Permet de lire le fichier et renvoie un tableau de String contenant toutes
	 * les lignes de ce fichier.
	 * 
	 * @param pFileName le nom du fichier
	 * @return tableau de String contenant toutes les lignes du fichier
	 * @throws IOException
	 */
	public static List<String> readFileLines(String pFileName) throws IOException {
		List<String> list = new ArrayList<String>();
		String lLigne = null;
		BufferedReader lIn;
		lIn = new BufferedReader(new FileReader((pFileName)));
		do {
			lLigne = lIn.readLine();
			if (lLigne != null && !"".equals(lLigne.trim())) {
				list.add(lLigne.trim());
			}
		} while (lLigne != null);
		lIn.close();
		return list;
	}

	/**
	 * M�thode qui permet de lire un fichier
	 * 
	 * @param pFileName
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String pFileName, boolean addWS) throws IOException {

		StringBuilder txt = new StringBuilder();
		String lLigne = null;
		BufferedReader lIn;
		lIn = new BufferedReader(new FileReader((pFileName)));
		do {
			lLigne = lIn.readLine();
			if (lLigne != null && !"".equals(lLigne)) {
				txt.append(lLigne);
				if (addWS) {
					txt.append(" ");
				}
			}
		} while (lLigne != null);
		lIn.close();
		return txt.toString();
	}

	/**
	 * M�thode qui supprime un fichier
	 * 
	 * @param file
	 */
	public static boolean removeFile(String filePath) throws IOException {
		File file = new File(filePath);
		return file.delete();
	}

	public static void deleteFolderFiles(File file) throws IOException {

		for (File childFile : file.listFiles()) {

			if (!childFile.isDirectory()) {
				childFile.delete();
			}
		}

	}

	public static void append(String filename, String text) throws IOException {
		BufferedWriter bufWriter = null;
		FileWriter fileWriter = null;

		fileWriter = new FileWriter(filename, true);
		bufWriter = new BufferedWriter(fileWriter);
		// Ins�rer la nouvelle ligne
		bufWriter.write(text);
		// Ins�rer un saut de ligne
		bufWriter.newLine();
		// Fermer le flux
		bufWriter.close();
		fileWriter.close();

	}

	/**
	 * M�thode qui v�rifie si un fichier existe sur un chemin
	 * 
	 * @param filePathString
	 * @return
	 */
	public static boolean fileExists(String filePathString) {
		File f = new File(filePathString);
		if (f.exists() && !f.isDirectory()) {
			return true;
		}
		return false;
	}
	
	
	public static boolean folderExists(String filePathString) {
		File f = new File(filePathString);
		if (f.exists() && f.isDirectory()) {
			return true;
		}
		return false;
	}

	/**
	 * M�thode qui retourne le chemin absolu du projet
	 * 
	 * @return
	 */
	public static String getAbsolutePathProject() {

		return new File("").getAbsolutePath();
	}

	/**
	 * Permet de convertir un fichier en tableau binaire
	 * 
	 * @param handledDocument
	 * @return
	 * @throws IOException
	 */
	public static byte[] getByteArrayFromFile(final String handledDocument) throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final InputStream in = new FileInputStream(handledDocument);
		final byte[] buffer = new byte[500];
		int read = -1;
		while ((read = in.read(buffer)) > 0) {
			baos.write(buffer, 0, read);
		}
		in.close();
		return baos.toByteArray();
	}

	/**
	 * Convertir un byte array to file
	 * 
	 * @param path
	 * @param byteArray
	 * @throws IOException
	 */
	public static void byteArrayToFile(String path, byte[] byteArray) throws IOException {
		FileOutputStream fos = new FileOutputStream(path);
		fos.write(byteArray);
		fos.close();

	}

	/**
	 * 
	 * @param pFilePath
	 * @throws IOException
	 */
	public static void LauncheAssociatedAppForFile(String pFilePath) throws IOException {

		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().open(new File(pFilePath));
		}

	}

	public static boolean createFolder(String pFolderName) {

		boolean success = (new File(pFolderName)).mkdirs();

		return success;
	}

	public static List<File> getFolderFiles(String dir) {
		File folder = new File(dir);

		// loops over files available in the path except for hidden files.
		File[] listOfFiles = folder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return !file.isHidden();
			}
		});

		return Arrays.asList(listOfFiles);
	}

}
