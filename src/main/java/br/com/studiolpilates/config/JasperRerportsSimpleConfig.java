package br.com.studiolpilates.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsViewResolver;

import br.com.studiolpilates.model.filter.SimpleReportFiller;
import br.com.studiolpilates.report.SimpleReportExporter;

@Configuration
public class JasperRerportsSimpleConfig {

	// @Bean
	// public DataSource dataSource() {
	// return new
	// EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).addScript("classpath:employee-schema.sql").build();
	// }
	//

	@Bean
	public JasperReportsViewResolver getJasperReportsViewResolver() {
		JasperReportsViewResolver resolver = new JasperReportsViewResolver();
		resolver.setPrefix("classpath:/jasperreports/");
		resolver.setSuffix(".jasper");
		resolver.setReportDataKey("datasource");
		resolver.setViewNames("rpt_*");
		resolver.setViewClass(JasperReportsMultiFormatView.class);
		resolver.setOrder(0);
		return resolver;
	}

	@Bean
	public SimpleReportFiller reportFiller() {
		return new SimpleReportFiller();
	}

	@Bean
	public SimpleReportExporter reportExporter() {
		return new SimpleReportExporter();
	}

}
