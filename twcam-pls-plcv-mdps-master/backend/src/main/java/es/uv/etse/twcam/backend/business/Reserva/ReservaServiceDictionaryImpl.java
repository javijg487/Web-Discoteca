package es.uv.etse.twcam.backend.business.Reserva;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import es.uv.etse.twcam.backend.business.ElementNotExistsException;
import es.uv.etse.twcam.backend.business.GeneralException;
import es.uv.etse.twcam.backend.business.IncorrectElementException;

public class ReservaServiceDictionaryImpl implements ReservaService {

	private static ReservaServiceDictionaryImpl the;

	protected Map<Integer, Reserva> dictionary;
	protected int currentIndex;

	private ReservaServiceDictionaryImpl() {
		dictionary = new Hashtable<>();
		currentIndex = 0;
	}

	public static ReservaServiceDictionaryImpl getInstance() {

		if (the == null) {
			the = new ReservaServiceDictionaryImpl();
		}

		return the;
	}

	public static void clearInstance() {
		if (the != null) {
			the = null;
			the.dictionary.clear();
		}
	}

	@Override
	public List<Reserva> listAll() {
		List<Reserva> reservas = new ArrayList<>();
		reservas.addAll(dictionary.values());

		return reservas;
	}

	public ArrayList<Reserva> listByUser(String username) {
		ArrayList<Reserva> result = new ArrayList<Reserva>();
		for (Integer key : dictionary.keySet()) {
			if (dictionary.get(key).getUsuario().equals(username)) {
				result.add(dictionary.get(key));
			}
		}

		return result;
	}

	public Reserva getById(Integer id) {
		return dictionary.get(id);
	}

	@Override
	public Reserva update(Reserva reserva) throws GeneralException {
		if (reserva != null && dictionary.containsKey(reserva.getId())) {
			dictionary.put(reserva.getId(), reserva);
		} else {
			if (reserva == null)
				throw new IncorrectElementException("Reserva o su identificador son nulos");
			else
				throw new ElementNotExistsException("Reserva", reserva.getId());
		}

		return reserva;
	}

	public Reserva create(Reserva reserva) {
		reserva.setId(currentIndex);
		reserva.setEstado("Pendiente de Pago");

		dictionary.put(currentIndex, reserva);
		currentIndex += 1;

		return reserva;
	}
}
