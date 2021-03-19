package me.tomasztopola.tabCompleters;

import java.util.ArrayList;
import java.util.List;

public class MatchResults {
    public static List<String> match(String[] args, List<String> list, int index){
        List<String> result = new ArrayList<>();
        for(String item : list){
            if(item.toLowerCase().startsWith(args[index].toLowerCase()))
                result.add(item);
        }
        return result;
    }
}
