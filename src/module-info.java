module Test{

	requires javafx.controls;
	requires java.desktop;
	requires javafx.fxml;
    requires java.xml;
	requires javafx.graphics;

	opens business to javafx.graphics, javafx.fmxl;
	
	exports presentation;
	exports application;
    exports domain;
    
    opens application to javafx.fxml;
}