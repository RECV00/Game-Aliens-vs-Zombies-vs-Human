module Test{

	requires javafx.controls;
	requires java.desktop;
	requires javafx.fxml;
    requires java.xml;

	opens business to javafx.graphics, javafx.fmxl;
	
	exports presentation;
    exports domain;
}