package com.dcalabrese22.dan.maxpassusage;

import com.dcalabrese22.dan.maxpassusage.database.DbOperations;

/**
 * Created by dan on 12/19/17.
 */

public class ResortsBuilder {

    private static String[] resorts = {
            "Alyeska",
            "Mt Bachelor",
            "Belleayre Mountain",
            "Big Sky",
            "Blue Mountain",
            "Boreal",
            "Boyne Highlands",
            "Boyne Mountain",
            "Brighton",
            "Buck Hill",
            "Copper Mountain",
            "Crested Butte",
            "Crystal Mountain",
            "Cypress Mountain",
            "Eldora",
            "Fernie Alpine Resort",
            "Gore Mountain",
            "Granite Peak",
            "Kicking Horse",
            "Killington",
            "Kimberley",
            "Lee Canyon",
            "Loon",
            "Lutsen Mountains",
            "Mont Sainte Anne",
            "Mountain Creek",
            "Mountain High",
            "Nakiska",
            "Mount Sunapee",
            "Okemo",
            "Pico Mountain",
            "The Summit as Snoqualmie",
            "Snowshoe",
            "Solitude",
            "Steamboat",
            "Stoneham",
            "Stratton",
            "Sugarloaf",
            "Sunday River",
            "Tremblant",
            "Wachusett",
            "Windham",
            "Whiteface",
            "Winter Park"
    };

    private static double[][] coordinates = {
            {60.970464, -149.098716},//alyeska
            {44.003307, -121.678782},//mt bachelor
            {42.132373, -74.505290},//belleayre
            {45.285744, -111.401206},//big sky
            {40.822657, -75.512484},//blue mountain
            {39.336510, -120.349840},//boreal
            {45.469243, -84.935389},//boyne highlands
            {45.162881, -84.930061}, //boyne mountain
            {40.598023, -111.583185},//brighton
            {44.723846, -93.285619},//buck hill
            {39.502142, -106.150993},//copper mt
            {38.8989018,-106.965861},//crested butte
            {46.9477262,-121.473802},//crystal mt
            {49.3959766,-123.204647},//cypress mt
            {39.9372203,-105.582679},//eldora
            {49.4627402,-115.085719},//fernie
            {43.6826008,-73.991771},//gore
            {44.9324358,-89.680972},//granite peak
            {51.298787,-117.0483911},//kicking horse
            {43.619801,-72.80271},//killington
            {49.6885103,-116.0045958},//kimberly
            {36.30376220000001,-115.67963170000002},//lee canyon
            {44.0565389,-71.62957589999996},//loon
            {47.6640469,-90.71505860000002},//lutsen mts
            {47.074031,-70.90447370000004},//mont sainte anne
            {41.1907548,-74.50522339999998},//mt creek
            {34.3769418,-117.6915242},//mt high
            {50.9427028,-115.151097},//nakiska
            {43.3309775,-72.08111600000001},//mt sunapee
            {43.4018216,-72.71701280000002},//okemo
            {43.6621581,-72.84270029999999},//pico
            {47.4172588,-121.41303540000001},//summit at snoqualmie
            {38.4101348,-79.99123279999998},//showshoe
            {40.6197629,-111.59189600000002},//solitude
            {40.4571564,-106.804463},//steamboat
            {47.0270162,-71.3829404},//stoneham
            {43.113819,-72.90508999999997},//stratton
            {45.0641605,-70.33546619999998},//sugarloaf
            {44.4669465,-70.84651939999998},//sunday river
            {46.2098078,-74.58536070000002},//tremblant
            {42.503554,-71.88543299999998},//wachusett
            {42.29896,-74.25696700000003},//windham
            {44.353851,-73.86093399999999},//whiteface
            {39.886822,-105.76248980000003},//winter park
    };

    private DbOperations mOperator;

    public ResortsBuilder(DbOperations operator) {
        mOperator = operator;
    }

    public void createSkiAreas() {
        for (int i = 0; i < resorts.length; i++) {
            SkiArea skiArea = new SkiArea(resorts[i], coordinates[i][0], coordinates[i][1], 0);
            mOperator.insertSkiArea(skiArea);
        }
    }

    public static String[] getResortNames() {
        return resorts;
    }
}
