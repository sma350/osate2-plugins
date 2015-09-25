package org.osate.analysis.flows.reporting.exporters;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.osate.aadl2.modelsupport.util.AadlUtil;
import org.osate.aadl2.util.OsateDebug;
import org.osate.analysis.flows.reporting.model.Line;
import org.osate.analysis.flows.reporting.model.Report;
import org.osate.analysis.flows.reporting.model.ReportedCell;
import org.osate.analysis.flows.reporting.model.Section;

public class CsvExport extends GenericExport {
	
	public List<Double> LatencyValues = new ArrayList<Double>();

	public CsvExport(Report r) {
		super(r);
		fileExtension = "csv";
	}

	public StringBuffer getFileContent() {
		StringBuffer result;
		result = new StringBuffer();

		for (Section section : report.getSections()) {
			for (Line line : section.getLines()) {
				for (ReportedCell content : line.getContent()) {
					result.append(content.getMessage());
					result.append(",");
				}
				result.append(System.lineSeparator());
			}
			result.append(System.lineSeparator() + System.lineSeparator() + System.lineSeparator());
		}
		return result;
	}

	@Override
	public void save() {
		IFile file;
		InputStream input;
		StringBuffer reportContent;

		reportContent = getFileContent();

		if (getPath() != null) {
			file = ResourcesPlugin.getWorkspace().getRoot().getFile(getPath());
			if (file != null) {
				input = new ByteArrayInputStream(reportContent.toString().getBytes());
				try {
					if (file.exists()) {
						file.setContents(input, true, true, null);
					} else {
						AadlUtil.makeSureFoldersExist(getPath());
						file.create(input, true, null);
					}
				} catch (final CoreException e) {
					OsateDebug.osateDebug("CsvExport", "Fail to create the report");
				}
			}
		}
	}
	
	public void saveAndFill() {
		StringBuffer reportContent;
		reportContent = getFileContent();
		String line[] = reportContent.toString().split("\n");
		
		for(int i=0;i<line.length;i++)
		{
			if(line[i].contains("Latency Total"))
			{
				String[] arr = line[i].split(",",-1);
				LatencyValues.add(Double.parseDouble(arr[5].replaceAll("^\\.0123456789E-", "")));
			}
		}

	}

}
