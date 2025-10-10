// javascript
// arquivo: src/main/frontend/qr-scanner.js
let codeReader = null;

async function loadZXing() {
    // usa a UMD global se estiver disponível (incluir generated/zxing.min.js em index.html)
    if (typeof window !== 'undefined' && window.ZXing && window.ZXing.BrowserMultiFormatReader) {
        return window.ZXing.BrowserMultiFormatReader;
    }
    // fallback para ESM no CDN
    const mod = await import('https://unpkg.com/@zxing/library@0.18.6/esm/index.js');
    return mod.BrowserMultiFormatReader;
}

export async function startScanner(rootElement) {
    try {
        const root = rootElement;
        const video = (root.querySelector && root.querySelector('#qr-video')) || document.getElementById('qr-video');
        if (!video) {
            console.error('qr-scanner: video element not found (id="qr-video")');
            return;
        }

        const BrowserMultiFormatReader = await loadZXing();
        codeReader = new BrowserMultiFormatReader();
        window._zxingReader = codeReader;

        const devices = await codeReader.listVideoInputDevices();
        let deviceId = null;
        if (devices && devices.length) {
            const back = devices.find(d => /back|rear|traseira/i.test(d.label));
            deviceId = (back && back.deviceId) || devices[devices.length - 1].deviceId;
        }

        codeReader.decodeFromVideoDevice(deviceId, video, (result, err) => {
            if (result) {
                try {
                    if (root.$server && typeof root.$server.onDetected === 'function') {
                        // compatível com a interface usada anteriormente
                        const text = (typeof result.getText === 'function') ? result.getText() : (result.text || '');
                        root.$server.onDetected(text);
                    } else {
                        console.warn('qr-scanner: servidor não expõe onDetected');
                    }
                } catch (e) {
                    console.error('qr-scanner: erro ao chamar $server.onDetected', e);
                }
                codeReader.reset();
                window._zxingReader = null;
                codeReader = null;
            } else if (err && !(err.name === 'NotFoundException')) {
                console.debug('qr-scanner decode error', err);
            }
        });
    } catch (e) {
        console.error('qr-scanner startScanner error', e);
    }
}

export function stopScanner() {
    if (window._zxingReader && typeof window._zxingReader.reset === 'function') {
        window._zxingReader.reset();
    }
    window._zxingReader = null;
    codeReader = null;
}
