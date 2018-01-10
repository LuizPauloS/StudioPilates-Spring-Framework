package br.com.studiolpilates;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import br.com.studiolpilates.config.JasperRerportsSimpleConfig;
import br.com.studiolpilates.model.filter.SimpleReportFiller;
import br.com.studiolpilates.report.SimpleReportExporter;

public class TesteReport {

	public static void main(String[] args) {
//		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//		ctx.register(JasperRerportsSimpleConfig.class);
//		ctx.refresh();

		SimpleReportFiller simpleReportFiller = new SimpleReportFiller();
		simpleReportFiller.setReportFileName("relatorio_pacientes.jrxml");
		simpleReportFiller.compileReport();

		SimpleReportExporter simpleExporter = new SimpleReportExporter();
		simpleExporter.setJasperPrint(simpleReportFiller.getJasperPrint());

		simpleExporter.exportToPdf("relatorio_pacientes.pdf", "baeldung");
	}
}
