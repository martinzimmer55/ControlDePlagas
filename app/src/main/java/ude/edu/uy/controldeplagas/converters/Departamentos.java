package ude.edu.uy.controldeplagas.converters;

import android.util.ArrayMap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import ude.edu.uy.controldeplagas.connection.HttpUrlConnection;

/**
 * Created by mzimmer on 27/11/17.
 */

public class Departamentos {

    //con esto obtengo un mapa con clave=nombre de departamento y valor=url
    public static Map<String, String> getDatosDepartamentos(String json) {
        JSONArray deptosArray = null;
        Map<String, String> deptosMap = new ArrayMap<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject embedded = jsonObject.getJSONObject("_embedded");
            deptosArray = embedded.getJSONArray("departamentoes");
            for (int i = 0; i < deptosArray.length(); i++) {
                JSONObject depto = deptosArray.getJSONObject(i);
                String nombre = depto.getString("nombre");
                JSONObject links = depto.getJSONObject("_links");
                JSONObject self = links.getJSONObject("self");
                String url = self.getString("href");
                deptosMap.put(nombre, url);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return deptosMap;
    }
    public static ArrayList<String> getListaDepartamentos (Map<String,String> mapa) {
        //Luego para sacar la lista de los nombres:
        ArrayList<String> mStringList = new ArrayList<String>();
        for (Map.Entry<String, String> entry : mapa.entrySet()) {
            mStringList.add(entry.getKey());
        }
        return  mStringList;
    }
}
