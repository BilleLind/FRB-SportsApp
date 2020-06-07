package com.example.eksamensprojekt.data.repository;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.eksamensprojekt.data.model.Oovelser;

import java.util.ArrayList;
import java.util.List;

//Singleton mønsteret
public class OovelserOversigtRepository {

    private static OovelserOversigtRepository instance;
    private ArrayList<Oovelser> dataSet = new ArrayList<>();

    public static OovelserOversigtRepository getInstance() {
        if(instance == null) {
            instance = new OovelserOversigtRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Oovelser>> getOovelser() { //Lyder som om vi henter data fra en online kilde
        setOovelser();
        MutableLiveData<List<Oovelser>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setOovelser() {
        dataSet.add(new Oovelser( "Liggende bækkenløft", "https://exorlive.com/video/?culture=da-DK&ex=11","https://media.exorlive.com/?id=11&filetype=jpg&env=production"));
        dataSet.add(new Oovelser( "Etbens knæbøj", "https://exorlive.com/video/?culture=da-DK&ex=605","https://media.exorlive.com/?id=605&filetype=jpg&env=production"));
        dataSet.add(new Oovelser( "Bækkenløft m/knæstræk", "https://exorlive.com/video/?culture=da-DK&ex=711","https://media.exorlive.com/?id=711&filetype=jpg&env=production"));
        dataSet.add(new Oovelser( "Armstræk", "https://exorlive.com/video/?culture=da-DK&ex=29","https://media.exorlive.com/?id=29&filetype=jpg&env=production"));
        dataSet.add(new Oovelser( "Mavebøjning", "https://exorlive.com/video/?culture=da-DK&ex=16","https://media.exorlive.com/?id=16&filetype=jpg&env=production"));
        dataSet.add(new Oovelser( "Lateral lunge", "https://exorlive.com/video/?culture=da-DK&ex=8820","https://media.exorlive.com/?id=8820&filetype=jpg&env=production"));
        dataSet.add(new Oovelser( "Hoppende knæbøjninger", "https://exorlive.com/video/?culture=da-DK&ex=10306","https://media.exorlive.com/?id=10306&filetype=jpg&env=production"));
    }
}
