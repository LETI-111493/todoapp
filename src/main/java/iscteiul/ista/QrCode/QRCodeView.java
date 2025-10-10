// java
package iscteiul.ista.QrCode;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Base64;

import iscteiul.ista.base.ui.MainLayout;

@Route(value = "qr", layout = MainLayout.class)
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
                text.setInvalid(true);
                text.setErrorMessage("Por favor insira um texto ou URL.");
                return;
            }
            text.setInvalid(false);

            byte[] png = qrService.generateQRCode(value, 300);
            String base64 = Base64.getEncoder().encodeToString(png);
            String dataUrl = "data:image/png;base64," + base64;
            img.setSrc(dataUrl);
        });

        // Layout
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setPadding(true);
        setSpacing(true);

        add(text, gen, img);
    }
}

