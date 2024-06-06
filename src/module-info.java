module Test{

	requires javafx.controls;
	requires java.desktop;
	requires javafx.fxml;
    requires java.xml;
	requires javafx.graphics;
	
	exports presentation;
    exports domain;
    
    opens business to javafx.graphics, javafx.fmxl;
}