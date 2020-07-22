package com.happyride.eservice.configuration;

import java.io.File;

public class GlobalUtils {

    public static String nameToSlug(String itemTitleName){
        String replaceAllCharacterWithSpace  =  itemTitleName.replaceAll("[^\\w]"," ").toLowerCase();
        String afterSpaceRemoveDuplicateSpace = replaceAllCharacterWithSpace.replaceAll("\\s+"," ");
        String replaceSpaceFinal = afterSpaceRemoveDuplicateSpace.replaceAll("[^\\w]","-");
        return replaceSpaceFinal;
    }


    public static String tomcatContextPathParent(String path) {
        return new File(path).getParent();
    }


}
