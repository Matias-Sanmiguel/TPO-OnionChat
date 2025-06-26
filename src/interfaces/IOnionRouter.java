package interfaces;

import java.util.List;

public interface IOnionRouter {
    IMensaje cifrarOnion(IMensaje mensaje, List<INodo> ruta) throws Exception;

    IMensaje descifrarCapa(IMensaje mensajeCifrado, IClavePrivada clavePrivada) throws Exception;
}
