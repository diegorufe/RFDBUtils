package com.RFDBUtils.comparators;

import java.io.File;
import java.util.Comparator;

/**
 * Class for compare file names
 * 
 * @author diego
 *
 */
public class FileNameComparator implements Comparator<File> {
	public int compare(File a, File b) {
		return a.getName().compareTo(b.getName());
	}
}