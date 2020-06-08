package com.example.eksamensprojekt.data.repository;

import androidx.lifecycle.MutableLiveData;
import com.example.eksamensprojekt.data.model.Oovelser;

import java.util.ArrayList;
import java.util.List;

//Repository klassen er hvor vi samler vores data
//Singleton mønsteret
public class OovelserRepository {

    private static OovelserRepository instance; //Vi laver et instance for at undgå at skabe en hær af åbne forbindelser til evt. web service/api/caches
    private ArrayList<Oovelser> dataSet = new ArrayList<>();

    public static OovelserRepository getInstance() {
        if(instance == null) {
            instance = new OovelserRepository();
        }
        return instance;
    }
    //Her efterligner vi hvordan det ville se ud hvis vi hentede fra en online kilde
    public MutableLiveData<List<Oovelser>> getOovelser() {
        dataSet.clear();
        setOovelser(); //efterligner at hente fra online kilde
        MutableLiveData<List<Oovelser>> data = new MutableLiveData<>(); //Forbinder den data med et MutableLiveData objekt
        data.setValue(dataSet);
        return data;
    }

    private void setOovelser() { //Her ville man typisk hente data fra en database
        dataSet.add(new Oovelser( "Liggende bækkenløft", "https://exorlive.com/video/?culture=da-DK&ex=11","https://media.exorlive.com/?id=11&filetype=jpg&env=production"));
        dataSet.add(new Oovelser( "Etbens knæbøj", "https://exorlive.com/video/?culture=da-DK&ex=605","https://media.exorlive.com/?id=605&filetype=jpg&env=production"));
        dataSet.add(new Oovelser( "Bækkenløft m/knæstræk", "https://exorlive.com/video/?culture=da-DK&ex=711","https://media.exorlive.com/?id=711&filetype=jpg&env=production"));
        dataSet.add(new Oovelser( "Armstræk", "https://exorlive.com/video/?culture=da-DK&ex=29","https://media.exorlive.com/?id=29&filetype=jpg&env=production"));
        dataSet.add(new Oovelser( "Mavebøjning", "https://exorlive.com/video/?culture=da-DK&ex=16","https://media.exorlive.com/?id=16&filetype=jpg&env=production"));
        dataSet.add(new Oovelser( "Lateral lunge", "https://exorlive.com/video/?culture=da-DK&ex=8820","https://media.exorlive.com/?id=8820&filetype=jpg&env=production"));
        dataSet.add(new Oovelser( "Hoppende knæbøjninger", "https://exorlive.com/video/?culture=da-DK&ex=10306","https://media.exorlive.com/?id=10306&filetype=jpg&env=production"));
    }
}
