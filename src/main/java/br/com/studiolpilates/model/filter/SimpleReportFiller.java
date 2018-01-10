package br.com.studiolpilates.model.filter;

import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class SimpleReportFiller {

	private String reportFileName;
	private JasperReport jasperReport;
	private JasperPrint jasperPrint;

	@Autowired
	private DataSource dataSource;

	private Map<String, Object> parameters;

	public SimpleReportFiller() {
		parameters = new HashMap<>();
	}

	public void prepareReport() {
		compileReport();
		fillReport();
	}

	/**
	 * Os arquivos JRXML precisam ser compilados para que o mecanismo de relatório possa preenchê-los com dados.
	 * Vamos executar esta operação com a ajuda da classe JasperCompilerManager
	 */
	public void compileReport() {
		try {
			InputStream reportStream = getClass().getResourceAsStream("/relatorios/".concat(reportFileName));
			jasperReport = JasperCompileManager.compileReport(reportStream);
			//Para evitar compilá-lo sempre que possível, podemos salvá-lo em um arquivo
			JRSaver.saveObject(jasperReport, reportFileName.replace(".jrxml", ".jasper"));
		} catch (JRException ex) {
			Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void fillReport() {
		try {
			//Agora, podemos preencher o relatório
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());
		} catch (JRException | SQLException ex) {
			Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public String getReportFileName() {
		return reportFileName;
	}

	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}

	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}
}
