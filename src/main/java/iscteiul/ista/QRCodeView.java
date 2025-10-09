// java
package iscteiul.ista.todoapp.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import iscteiul.ista.todoapp.service.QRCodeService;

import java.util.Base64;

@Route("qr")
@PageTitle("QR Generator")
public class QRCodeView extends VerticalLayout {

    private final QRCodeService qrService;

    @Autowired
    public QRCodeView(QRCodeService qrService) {
        this.qrService = qrService;

        TextField text = new TextField("Texto ou URL");
        text.setWidth("400px");
        Button gen = new Button("Gerar QR");
        Image img = new Image();
        img.setAlt("QR Code");
        img.setWidth("200px");

        gen.addClickListener(e -> {
            String value = text.getValue();
            if (value == null || value.isBlank()) {
                return;
            }
            byte[] png = qrService.generateQRCode(value, 300);
            String base64 = Base64.getEncoder().encodeToString(png);
            String dataUrl = "data:image/png;base64," + base64;
            img.setSrc(dataUrl);
        });

        add(text, gen, img);
    }
}
