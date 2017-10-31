package com.vscholars.stack2code.aicte_phaseone;


import android.os.AsyncTask;
import android.util.Log;

import com.vscholars.stack2code.aicte_phaseone.DataItems.ApprovedInstituteDataItem;
import com.vscholars.stack2code.aicte_phaseone.DataItems.ApprovedInstitutesCourseDetailsDataItems;
import com.vscholars.stack2code.aicte_phaseone.DataItems.ApprovedInstitutesFacultyDetailsDataItems;
import com.vscholars.stack2code.aicte_phaseone.DataItems.ClosedCoursesDataItems;
import com.vscholars.stack2code.aicte_phaseone.DataItems.ClosedInstitutesDataItems;
import com.vscholars.stack2code.aicte_phaseone.DataItems.DashboardDataItems;
import com.vscholars.stack2code.aicte_phaseone.DataItems.NriPioFciDataItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by vineet_jain on 21/6/17.
 */

public class jsonClasses{

    private DashboardDataItems J_dashboardDataItem;
    private ApprovedInstituteDataItem J_approvedInstitutions;
    private ClosedCoursesDataItems J_closedCoursesDataItems;
    private ClosedInstitutesDataItems J_closedInstitutesDataItems;
    private NriPioFciDataItems J_NriPioFciDataItems;
    private ApprovedInstitutesCourseDetailsDataItems J_courseDetails;
    private ApprovedInstitutesFacultyDetailsDataItems J_facultyDetails;

    public json_dashboardData J_json_dashboardData;
    public json_approvedInstitute J_json_approvedInstitutions;
    public json_closedcourses J_json_closedCourses;
    public json_closedinstitutes J_json_closedInstitutes;
    public json_nriPio J_json_NriPioFci;
    public json_courseDetails J_json_courseDetails;
    public json_facultyDetails J_json_facultyDetails;
    public json_hintListLoader J_json_hintList;
    public json_signUp J_signUp;
    public json_otpverify J_otpVerify;
    public json_smslogin J_smsLogin;
    public json_otpresend J_otpResend;
    public json_logout J_jsonLogOut;
    public json_notification J_Notification;

    public String[] defaultValuesDashboard;
    public String[][][] data;
    public LinkedHashMap<String,Integer>categories;
    public LinkedHashMap<Integer,String>categoriesNames;
    public String totalEntries;
    public ArrayList<String>hintsList;
    public String response="letsSee",registrationStatus=null;
    public int success=-1;

    private String str;

    jsonClasses(String message){

        if (message.equals("dashboard")){

            J_json_dashboardData=new json_dashboardData();
            J_dashboardDataItem=new DashboardDataItems();
            defaultValuesDashboard=new String[8];

        }else if (message.equals("approved_institutes")){

            J_json_approvedInstitutions=new json_approvedInstitute();
            J_approvedInstitutions=new ApprovedInstituteDataItem();
            J_json_hintList=new json_hintListLoader();
            hintsList=new ArrayList<String>();

        }else if (message.equals("nri/pio-fn-ciwg/tp")){

            J_json_NriPioFci=new json_nriPio();
            J_NriPioFciDataItems=new NriPioFciDataItems();

        }else if (message.equals("closed_courses")){

            J_json_closedCourses=new json_closedcourses();
            J_closedCoursesDataItems=new ClosedCoursesDataItems();

        }else if (message.equals("closed_institutes")){

            J_json_closedInstitutes=new json_closedinstitutes();
            J_closedInstitutesDataItems=new ClosedInstitutesDataItems();

        }else if (message.equals("course_details")){

            J_json_courseDetails=new json_courseDetails();
            J_courseDetails=new ApprovedInstitutesCourseDetailsDataItems();

        }else if (message.equals("faculty_details")){

            J_json_facultyDetails=new json_facultyDetails();
            J_facultyDetails=new ApprovedInstitutesFacultyDetailsDataItems();

        }else if (message.equals("signUp")){

            J_signUp=new json_signUp();

        }else if (message.equals("otpVerify")){

            J_otpVerify=new json_otpverify();

        }else if (message.equals("smsLogin")){

            J_smsLogin=new json_smslogin();

        }else if (message.equals("otpResend")){

            J_otpResend=new json_otpresend();

        }else if (message.equals("logOut")){

            J_jsonLogOut=new json_logout();

        }else if (message.equals("notification")){

            J_Notification=new json_notification();

        }
    }


    protected class json_dashboardData extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {

            try {

                 /*
                   s[0] year
                   s[1] program
                   s[2] level
                   s[3] institutiontype
                   s[4] statetype
                   s[5] Minority
                   s[6] Women
                 */

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("\"year\"", s[0]));
                params.add(new BasicNameValuePair("\"program\"", s[1]));
                params.add(new BasicNameValuePair("\"level\"", s[2]));
                params.add(new BasicNameValuePair("\"institutiontype\"", s[3]));
                params.add(new BasicNameValuePair("\"state\"", s[4]));
                params.add(new BasicNameValuePair("\"Minority\"", s[5]));
                params.add(new BasicNameValuePair("\"Women\"", s[6]));
                List<NameValuePair> actual=new ArrayList<>();
                String paramsnew="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                paramsnew=paramsnew.replaceAll("=",":");
                actual.add(new BasicNameValuePair("paramsSent",paramsnew));

//                 getting JSON Object
//                 Note that create product url accepts POST method

                JSONObject jsonO =JSONParser.makeHttpRequest("http://www.facilities.aicte-india.org/dashboard/mobile/college/dashboardserver.php", "POST", actual);
                Log.d("key",actual.toString());
                Log.d("response",jsonO.toString());

                int success=jsonO.getInt("success");
                Log.d("success",success+"");

                if (success == 1) {

                    JSONObject Jo = jsonO.getJSONObject("records");
                    defaultValuesDashboard[0]=J_dashboardDataItem.J_faculties=Jo.getString("faculties");
                    defaultValuesDashboard[1]=J_dashboardDataItem.J_placements=Jo.getString("placed");
                    defaultValuesDashboard[2]=J_dashboardDataItem.J_studentsPassed=Jo.getString("passed");
                    defaultValuesDashboard[3]=J_dashboardDataItem.J_enrollment=Jo.getString("enrollment");
                    defaultValuesDashboard[4]=J_dashboardDataItem.J_totalIntake=Jo.getString("intake");
                    defaultValuesDashboard[5]=J_dashboardDataItem.J_totalInstitutes=Jo.getString("institutecount");
                    defaultValuesDashboard[6]=J_dashboardDataItem.J_newInstitutes=Jo.getString("newinstitute");
                    defaultValuesDashboard[7]=J_dashboardDataItem.J_closedInstitutes=Jo.getString("closedinstitute");

                    str=Jo.toString();

                } else {

                    str= "error in data fetch please try latter";
                }


            } catch (Exception e) {

                e.printStackTrace();
                str= "fatal error";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            return;
        }

    }


    protected class json_approvedInstitute extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {
            try {
                 /*
                   s[0] year
                   s[1] program
                   s[2] level
                   s[3] institutiontype
                   s[4] state
                   s[5] Minority
                   s[6] Women
                 */


                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("\"year\"", s[0]));
                params.add(new BasicNameValuePair("\"program\"", s[1]));
                params.add(new BasicNameValuePair("\"level\"", s[2]));
                params.add(new BasicNameValuePair("\"institutiontype\"", s[3]));
                params.add(new BasicNameValuePair("\"state\"", s[4]));
                params.add(new BasicNameValuePair("\"Minority\"", s[5]));
                params.add(new BasicNameValuePair("\"Women\"", s[6]));
                params.add(new BasicNameValuePair("\"course\"",s[7]));
                params.add(new BasicNameValuePair("\"str\"",s[8]));
                params.add(new BasicNameValuePair("\"offset\"",s[9]));
                List<NameValuePair> actual=new ArrayList<>();
                String paramsnew="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                paramsnew=paramsnew.replaceAll("=",":");
                actual.add(new BasicNameValuePair("paramsSent",paramsnew));
//                 getting JSON Object
//                 Note that create product url accepts POST method

                JSONObject jsonO =JSONParser.makeHttpRequest("http://www.facilities.aicte-india.org/dashboard/mobile/college/approvedinstitute.php", "POST", actual);
                Log.d("paramsSent",actual.toString());
                Log.d("JSONobj",jsonO.toString());

                int success=jsonO.getInt("success");
                if (success == 1) {
                    int categorySize=0;
                    int nameCount=-1;
                    categories=new LinkedHashMap<String,Integer>();
                    categoriesNames=new LinkedHashMap<>();
                    JSONArray Ja=jsonO.getJSONArray("list");

                    if (Ja.length()!=0){

                    for(int i=0;i<Ja.length();i++){
                        JSONObject Jo=Ja.getJSONObject(i);
                        J_approvedInstitutions.J_district=Jo.getString("c_name");
                        if (categories.containsKey(J_approvedInstitutions.J_district)) {
                            categorySize=categories.get(J_approvedInstitutions.J_district);
                            ++categorySize;
                            categories.put(J_approvedInstitutions.J_district,categorySize);
                        }else {
                            categories.put(J_approvedInstitutions.J_district,1);
                            ++nameCount;
                            categoriesNames.put(nameCount,J_approvedInstitutions.J_district);
                        }
                    }

                    for (int i=0;i<categories.size();++i){
                        if (i==0){
                            data=new String[categories.size()][][];
                        }
                        for (int j=0;j<categories.get(categoriesNames.get(i));++j){
                            if (j==0){
                                data[i]=new String[categories.get(categoriesNames.get(i))][];
                            }
                            for (int k=0;k<8;++k) {
                                data[i][j]=new String[8];
                            }
                        }
                    }
                    for (int i=0;i<categories.size();++i) {
                        ArrayList<Integer> used = new ArrayList<Integer>();
                        for (int k = 0; k < categories.get(categoriesNames.get(i)); ++k) {
                            for (int j = 0; j < Ja.length(); j++) {
                                JSONObject Jo = Ja.getJSONObject(j);
                                if (categoriesNames.get(i).equals(Jo.getString("c_name")) && used.contains(j) == false) {
                                    data[i][k][0] = J_approvedInstitutions.J_aicteid = Jo.getString("c_id");
                                    data[i][k][1] = J_approvedInstitutions.J_name = Jo.getString("c_name");
                                    data[i][k][2] = J_approvedInstitutions.J_address = Jo.getString("c_adds");
                                    data[i][k][3] = J_approvedInstitutions.J_district = Jo.getString("c_dist");
                                    data[i][k][4] = J_approvedInstitutions.J_institutionType = Jo.getString("c_type");
                                    data[i][k][5] = J_approvedInstitutions.J_women = Jo.getString("c_women");
                                    data[i][k][6] = J_approvedInstitutions.J_minority = Jo.getString("c_minior");
                                    data[i][k][7] = J_approvedInstitutions.J_permanentId=Jo.getString("c_pid");
                                    if(jsonO.getString("total_rows")!="null"){

                                        totalEntries=jsonO.getString("total_rows");

                                    }else{

                                        totalEntries="previousOne";

                                    }
                                    used.add(j);
                                    break;
                                }
                            }
                        }
                    }
                    }else {
                        categoriesNames.put(-1,"-1");
                    }
                } else {
                    str= "error in data fetch please try latter";
                }

            } catch (Exception e) {
                e.printStackTrace();
                response="nothingReceived";
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
        }
    }

    protected class json_closedcourses extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {
            try {
                 /*
                   s[0] year
                 */

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("\"year\"", s[0]));
                params.add(new BasicNameValuePair("\"str\"",s[1]));
                params.add(new BasicNameValuePair("\"offset\"",s[3]));
                List<NameValuePair> actual=new ArrayList<>();
                String paramsnew="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                paramsnew=paramsnew.replaceAll("=",":");
                actual.add(new BasicNameValuePair("paramsSent",paramsnew));

//                 getting JSON Object
//                 Note that create product url accepts POST method
                JSONObject jsonO =JSONParser.makeHttpRequest("http://www.facilities.aicte-india.org/dashboard/mobile/college/closedcourses.php", "POST", actual);
                Log.d("paramsSent",actual.toString());
                Log.d("JSONobj",jsonO.toString());

                int success=jsonO.getInt("success");
                int count=jsonO.getInt("count");

                if (success == 1) {

                    int categorySize=0;
                    int nameCount=-1;
                    categories=new LinkedHashMap<String,Integer>();
                    categoriesNames=new LinkedHashMap<>();
                    JSONArray Ja=jsonO.getJSONArray("list");

                    if (Ja.length()!=0) {

                        for (int i = 0; i < count; i++) {
                            JSONObject Jo = Ja.getJSONObject(i);
                            J_closedCoursesDataItems.J_state = Jo.getString("corname");
                            if (categories.containsKey(J_closedCoursesDataItems.J_state)) {
                                categorySize = categories.get(J_closedCoursesDataItems.J_state);
                                ++categorySize;
                                categories.put(J_closedCoursesDataItems.J_state, categorySize);
                            } else {
                                categories.put(J_closedCoursesDataItems.J_state, 1);
                                ++nameCount;
                                categoriesNames.put(nameCount, J_closedCoursesDataItems.J_state);
                            }
                        }

                        for (int i = 0; i < categories.size(); ++i) {
                            if (i == 0) {
                                data = new String[categories.size()][][];
                            }
                            for (int j = 0; j < categories.get(categoriesNames.get(i)); ++j) {
                                if (j == 0) {
                                    data[i] = new String[categories.get(categoriesNames.get(i))][];
                                }
                                for (int k = 0; k < 11; ++k) {
                                    data[i][j] = new String[11];
                                }
                            }
                        }

                        for (int i = 0; i < categories.size(); ++i) {
                            ArrayList<Integer> used = new ArrayList<Integer>();
                            for (int k = 0; k < categories.get(categoriesNames.get(i)); ++k) {
                                for (int j = 0; j < Ja.length(); j++) {
                                    JSONObject Jo = Ja.getJSONObject(j);
                                    if (categoriesNames.get(i).equals(Jo.getString("corname")) && used.contains(j) == false) {
                                        data[i][k][0] = J_closedCoursesDataItems.J_aicteid = Jo.getString("colid");
                                        data[i][k][1] = J_closedCoursesDataItems.J_insName = Jo.getString("collegename");
                                        data[i][k][2] = J_closedCoursesDataItems.J_insType = Jo.getString("type");
                                        data[i][k][3] = J_closedCoursesDataItems.J_state = Jo.getString("state");
                                        data[i][k][4] = J_closedCoursesDataItems.J_district = Jo.getString("district");
                                        data[i][k][5] = J_closedCoursesDataItems.J_courseId = Jo.getString("corid");
                                        data[i][k][6] = J_closedCoursesDataItems.J_university = Jo.getString("university");
                                        data[i][k][7] = J_closedCoursesDataItems.J_level = Jo.getString("corlevel");
                                        data[i][k][8] = J_closedCoursesDataItems.J_course = Jo.getString("corname");
                                        data[i][k][9] = J_closedCoursesDataItems.J_shift = Jo.getString("corshift");
                                        data[i][k][10] = J_closedCoursesDataItems.J_fullOrPartTime = Jo.getString("corfullpart");
                                        if(jsonO.getString("total_rows")!="null"){

                                            totalEntries=jsonO.getString("total_rows");

                                        }else{

                                            totalEntries="previousOne";

                                        }
                                        used.add(j);
                                        break;
                                    }
                                }
                            }
                        }
                    }else {
                        categoriesNames.put(-1,"-1");
                    }
                } else {
                    str= "error in data fetch please try latter";
                }


            } catch (Exception e) {
                e.printStackTrace();
                response="nothingReceived";
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
        }
    }

    protected class json_closedinstitutes extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {
            try {
                 /*
                   s[0] year
                 */

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("\"year\"", s[0]));
                params.add(new BasicNameValuePair("\"str\"",s[1]));
                params.add(new BasicNameValuePair("\"offset\"",s[3]));
                List<NameValuePair> actual=new ArrayList<>();
                String paramsnew="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                paramsnew=paramsnew.replaceAll("=",":");
                actual.add(new BasicNameValuePair("paramsSent",paramsnew));

//                 getting JSON Object
//                 Note that create product url accepts POST method
                JSONObject jsonO =JSONParser.makeHttpRequest("http://www.facilities.aicte-india.org/dashboard/mobile/college/closedinstitutes.php", "POST", actual);
                Log.d("paramsSent",actual.toString());
                Log.d("JSONobj",jsonO.toString());

                int success=jsonO.getInt("success");
                int count=jsonO.getInt("objects");

                if (success == 1) {

                    int categorySize=0;
                    int nameCount=-1;
                    categories=new LinkedHashMap<String,Integer>();
                    categoriesNames=new LinkedHashMap<>();
                    JSONArray Ja=jsonO.getJSONArray("list");

                    if (Ja.length()!=0) {

                        for (int i = 0; i < count; i++) {
                            JSONObject Jo = Ja.getJSONObject(i);
                            J_closedInstitutesDataItems.J_state = Jo.getString("collegename");
                            if (categories.containsKey(J_closedInstitutesDataItems.J_state)) {
                                categorySize = categories.get(J_closedInstitutesDataItems.J_state);
                                ++categorySize;
                                categories.put(J_closedInstitutesDataItems.J_state, categorySize);
                            } else {
                                categories.put(J_closedInstitutesDataItems.J_state, 1);
                                ++nameCount;
                                categoriesNames.put(nameCount, J_closedInstitutesDataItems.J_state);
                            }
                        }
                        for (int i = 0; i < categories.size(); ++i) {
                            if (i == 0) {
                                data = new String[categories.size()][][];
                            }
                            for (int j = 0; j < categories.get(categoriesNames.get(i)); ++j) {
                                if (j == 0) {
                                    data[i] = new String[categories.get(categoriesNames.get(i))][];
                                }
                                for (int k = 0; k < 7; ++k) {
                                    data[i][j] = new String[7];
                                }
                            }
                        }
                        for (int i = 0; i < categories.size(); ++i) {
                            ArrayList<Integer> used = new ArrayList<Integer>();
                            for (int k = 0; k < categories.get(categoriesNames.get(i)); ++k) {
                                for (int j = 0; j < Ja.length(); j++) {
                                    JSONObject Jo = Ja.getJSONObject(j);
                                    if (categoriesNames.get(i).equals(Jo.getString("collegename")) && used.contains(j) == false) {
                                        data[i][k][0] = J_closedInstitutesDataItems.J_aicteId = Jo.getString("colid");
                                        data[i][k][1] = J_closedInstitutesDataItems.J_instituteName = Jo.getString("collegename");
                                        data[i][k][2] = J_closedInstitutesDataItems.J_state = Jo.getString("state");
                                        data[i][k][3] = J_closedInstitutesDataItems.J_district = Jo.getString("district");
                                        data[i][k][4] = J_closedInstitutesDataItems.J_institute = Jo.getString("type");
                                        data[i][k][5] = J_closedInstitutesDataItems.J_address = Jo.getString("address");
                                        data[i][k][6] = J_closedInstitutesDataItems.J_city = Jo.getString("City");
                                        if(jsonO.getString("total_rows")!="null"){

                                            totalEntries=jsonO.getString("total_rows");

                                        }else{

                                            totalEntries="previousOne";

                                        }
                                        used.add(j);
                                        break;
                                    }
                                }
                            }
                        }
                    }else {
                        categoriesNames.put(-1,"-1");
                    }
                } else {
                    str= "error in data fetch please try latter";
                }


            } catch (Exception e) {
                e.printStackTrace();
                response="nothingReceived";
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
        }
    }

    protected class json_nriPio extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... s) {
            try {
                 /*
                   s[0] year
                 */

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("\"year\"", s[0]));
                params.add(new BasicNameValuePair("\"q1\"",s[1]));
                params.add(new BasicNameValuePair("\"str\"",s[2]));
                params.add(new BasicNameValuePair("\"offset\"",s[4]));
                List<NameValuePair> actual=new ArrayList<>();
                String paramsnew="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                paramsnew=paramsnew.replaceAll("=",":");
                actual.add(new BasicNameValuePair("paramsSent",paramsnew));

//                 getting JSON Object
//                 Note that create product url accepts POST method
                JSONObject jsonO =JSONParser.makeHttpRequest("http://www.facilities.aicte-india.org/dashboard/mobile/college/nripiofc.php", "POST", actual);
                Log.d("paramsSent",actual.toString());
                Log.d("jsonObj",jsonO.toString());

                int success=jsonO.getInt("success");
                //int count=jsonO.getInt("count");

                if (success == 1) {

                    int categorySize=0;
                    int nameCount=-1;
                    categories=new LinkedHashMap<String,Integer>();
                    categoriesNames=new LinkedHashMap<>();
                    JSONArray Ja=jsonO.getJSONArray("list");

                    if (Ja.length()!=0) {

                        for (int i = 0; i < Ja.length(); i++) {
                            JSONObject Jo = Ja.getJSONObject(i);
                            J_NriPioFciDataItems.J_state = Jo.getString("corname");
                            if (categories.containsKey(J_NriPioFciDataItems.J_state)) {
                                categorySize = categories.get(J_NriPioFciDataItems.J_state);
                                ++categorySize;
                                categories.put(J_NriPioFciDataItems.J_state, categorySize);
                            } else {
                                categories.put(J_NriPioFciDataItems.J_state, 1);
                                ++nameCount;
                                categoriesNames.put(nameCount, J_NriPioFciDataItems.J_state);
                            }
                        }
                        for (int i = 0; i < categories.size(); ++i) {
                            if (i == 0) {
                                data = new String[categories.size()][][];
                            }
                            for (int j = 0; j < categories.get(categoriesNames.get(i)); ++j) {
                                if (j == 0) {
                                    data[i] = new String[categories.get(categoriesNames.get(i))][];
                                }
                                for (int k = 0; k < 11; ++k) {
                                    data[i][j] = new String[11];
                                }
                            }
                        }
                        for (int i = 0; i < categories.size(); ++i) {
                            ArrayList<Integer> used = new ArrayList<Integer>();
                            for (int k = 0; k < categories.get(categoriesNames.get(i)); ++k) {
                                for (int j = 0; j < Ja.length(); j++) {
                                    JSONObject Jo = Ja.getJSONObject(j);
                                    if (categoriesNames.get(i).equals(Jo.getString("corname")) && used.contains(j) == false) {

                                        data[i][k][0] = J_NriPioFciDataItems.J_aicteId = Jo.getString("colid");
                                        data[i][k][1] = J_NriPioFciDataItems.J_instituteName = Jo.getString("collegename");
                                        data[i][k][2] = J_NriPioFciDataItems.J_state = Jo.getString("state");
                                        data[i][k][3] = J_NriPioFciDataItems.J_district = Jo.getString("district");
                                        data[i][k][4] = J_NriPioFciDataItems.J_institute = Jo.getString("type");
                                        data[i][k][5] = J_NriPioFciDataItems.J_program = Jo.getString("program");
                                        data[i][k][6] = J_NriPioFciDataItems.J_university = Jo.getString("university");
                                        data[i][k][7] = J_NriPioFciDataItems.J_level = Jo.getString("corlevel");
                                        data[i][k][8] = J_NriPioFciDataItems.J_nameOfCourses = Jo.getString("corname");
                                        data[i][k][9] = J_NriPioFciDataItems.J_approvedIntake = Jo.getString("intake");
                                        if(s[1].equals("[\"NRI\"]")){

                                            data[i][k][10]=J_NriPioFciDataItems.J_nriQuotaSeats="NRI"+Jo.getString("nri_quota");

                                        }else if (s[1].equals("[\"PIO\"]")){

                                            data[i][k][10]=J_NriPioFciDataItems.J_nriQuotaSeats="PIO"+Jo.getString("pio_quota");

                                        }else if (s[1].equals("[\"FC\"]")){

                                            data[i][k][10]="FC"+null;

                                        }
                                        if(jsonO.getString("total_rows")!="null"){

                                            totalEntries=jsonO.getString("total_rows");

                                        }else{

                                            totalEntries="previousOne";

                                        }
                                        used.add(j);
                                        break;
                                    }
                                }
                            }
                        }
                    }else {
                        categoriesNames.put(-1,"-1");
                    }
                } else {
                    str= "error in data fetch please try latter";
                }


            } catch (Exception e) {
                e.printStackTrace();
                response="nothingReceived";
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
        }

    }

    protected class json_courseDetails extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... s) {
            try {
                 /*
                   s[0] year
                 */

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("\"aicteid\"", s[0]));
                params.add(new BasicNameValuePair("\"year\"",s[1]));
                String sudo="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                sudo=sudo.replace('=',':');
                List<NameValuePair>actual=new ArrayList<>();
                actual.add(new BasicNameValuePair("paramsSent",sudo));
//                 getting JSON Object
//                 Note that create product url accepts POST method
                JSONObject jsonO =JSONParser.makeHttpRequest("http://www.facilities.aicte-india.org/dashboard/mobile/college/approvedcourse.php", "POST",actual);
                Log.d("paramsSent",actual.toString());
                Log.d("jsonObj",jsonO.toString());

                int success=jsonO.getInt("success");
                //int count=jsonO.getInt("count");

                if (success == 1) {

                    int categorySize=0;
                    int nameCount=-1;
                    categories=new LinkedHashMap<String,Integer>();
                    categoriesNames=new LinkedHashMap<>();
                    JSONArray Ja=jsonO.getJSONArray("list");

                    if (Ja.length()!=0) {

                        for (int i = 0; i < Ja.length(); i++) {
                            JSONObject Jo = Ja.getJSONObject(i);
                            J_courseDetails.J_course = Jo.getString("Course");
                            if (categories.containsKey(J_courseDetails.J_course)) {
                                categorySize = categories.get(J_courseDetails.J_course);
                                ++categorySize;
                                categories.put(J_courseDetails.J_course, categorySize);
                            } else {
                                categories.put(J_courseDetails.J_course, 1);
                                ++nameCount;
                                categoriesNames.put(nameCount, J_courseDetails.J_course);
                            }
                        }
                        for (int i = 0; i < categories.size(); ++i) {
                            if (i == 0) {
                                data = new String[categories.size()][][];
                            }
                            for (int j = 0; j < categories.get(categoriesNames.get(i)); ++j) {
                                if (j == 0) {
                                    data[i] = new String[categories.get(categoriesNames.get(i))][];
                                }
                                for (int k = 0; k < 11; ++k) {
                                    data[i][j] = new String[11];
                                }
                            }
                        }
                        for (int i = 0; i < categories.size(); ++i) {
                            ArrayList<Integer> used = new ArrayList<Integer>();
                            for (int k = 0; k < categories.get(categoriesNames.get(i)); ++k) {
                                for (int j = 0; j < Ja.length(); j++) {
                                    JSONObject Jo = Ja.getJSONObject(j);
                                    if (categoriesNames.get(i).equals(Jo.getString("Course")) && used.contains(j) == false) {
                                        data[i][k][0] = J_courseDetails.J_course = Jo.getString("Course");
                                        data[i][k][1] = J_courseDetails.J_programme = Jo.getString("Program");
                                        data[i][k][2] = J_courseDetails.J_university = Jo.getString("University");
                                        data[i][k][3] = J_courseDetails.J_courseLevel = Jo.getString("Level");
                                        data[i][k][4] = J_courseDetails.J_shift = Jo.getString("Shift");
                                        data[i][k][5] = J_courseDetails.J_fullPartTime = Jo.getString("ft_pt");
                                        data[i][k][6] = J_courseDetails.J_intake = Jo.getString("Intake");
                                        data[i][k][7] = J_courseDetails.J_enrolment = Jo.getString("Enroll");
                                        used.add(j);
                                        break;
                                    }
                                }
                            }
                        }
                    }else {
                        categoriesNames.put(-1,"-1");
                    }
                } else {
                    str= "error in data fetch please try latter";
                }


            } catch (Exception e) {
                e.printStackTrace();
                response="nothingReceived";
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
        }

    }

    protected class json_facultyDetails extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... s) {
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("\"aicteid\"", s[0]));
                params.add(new BasicNameValuePair("\"pid\"",s[1]));
                params.add(new BasicNameValuePair("\"year\"",s[2]));
                String sudo="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                sudo=sudo.replace('=',':');
                List<NameValuePair>actual=new ArrayList<>();
                actual.add(new BasicNameValuePair("paramsSent",sudo));
//                 getting JSON Object
//                 Note that create product url accepts POST method
                JSONObject jsonO =JSONParser.makeHttpRequest("http://www.facilities.aicte-india.org/dashboard/mobile/college/faculty.php", "POST", actual);
                Log.d("paramsSent",actual.toString());
                Log.d("jsonObj",jsonO.toString());

                int success=jsonO.getInt("success");
                //int count=jsonO.getInt("count");

                if (success == 1) {

                    int categorySize=0;
                    int nameCount=-1;
                    categories=new LinkedHashMap<String,Integer>();
                    categoriesNames=new LinkedHashMap<>();
                    JSONArray Ja=jsonO.getJSONArray("list");

                    if (Ja.length()!=0) {

                        for (int i = 0; i < Ja.length(); i++) {
                            JSONObject Jo = Ja.getJSONObject(i);
                            J_facultyDetails.J_name = Jo.getString("name");
                            if (categories.containsKey(J_facultyDetails.J_name)) {
                                categorySize = categories.get(J_facultyDetails.J_name);
                                ++categorySize;
                                categories.put(J_facultyDetails.J_name, categorySize);
                            } else {
                                categories.put(J_facultyDetails.J_name, 1);
                                ++nameCount;
                                categoriesNames.put(nameCount, J_facultyDetails.J_name);
                            }
                        }
                        for (int i = 0; i < categories.size(); ++i) {
                            if (i == 0) {
                                data = new String[categories.size()][][];
                            }
                            for (int j = 0; j < categories.get(categoriesNames.get(i)); ++j) {
                                if (j == 0) {
                                    data[i] = new String[categories.get(categoriesNames.get(i))][];
                                }
                                for (int k = 0; k < 7; ++k) {
                                    data[i][j] = new String[7];
                                }
                            }
                        }
                        for (int i = 0; i < categories.size(); ++i) {
                            ArrayList<Integer> used = new ArrayList<Integer>();
                            for (int k = 0; k < categories.get(categoriesNames.get(i)); ++k) {
                                for (int j = 0; j < Ja.length(); j++) {
                                    JSONObject Jo = Ja.getJSONObject(j);
                                    if (categoriesNames.get(i).equals(Jo.getString("name")) && used.contains(j) == false) {
                                        data[i][k][0] = J_facultyDetails.J_fid = Jo.getString("facid");
                                        data[i][k][1] = J_facultyDetails.J_name = Jo.getString("name");
                                        data[i][k][2] = J_facultyDetails.J_gender = Jo.getString("gender");
                                        data[i][k][3] = J_facultyDetails.J_designation = Jo.getString("designation");
                                        data[i][k][4] = J_facultyDetails.J_joiningDate = Jo.getString("joindate");
                                        data[i][k][5] = J_facultyDetails.J_specialisationArea = Jo.getString("specialisation");
                                        data[i][k][6] = J_facultyDetails.J_appointmentType = Jo.getString("appointment");
                                        used.add(j);
                                        break;
                                    }
                                }
                            }
                        }
                    }else {
                        categoriesNames.put(-1,"-1");
                    }
                } else {
                    str= "error in data fetch please try latter";
                }


            } catch (Exception e) {
                e.printStackTrace();
                response="nothingReceived";
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
        }

    }

    protected class json_hintListLoader extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {

            try {

                List<NameValuePair> actual = new ArrayList<>();
                actual.add(new BasicNameValuePair("paramsSent", null));

//                 getting JSON Object
//                 Note that create product url accepts POST method
                Log.d("reached","reached");
                JSONObject jsonO = JSONParser.makeHttpRequest("http://www.facilities.aicte-india.org/dashboard/mobile/college/hint.php", "POST", actual);
                Log.d("key", actual.toString());
                Log.d("response", jsonO.toString());

                JSONArray hintsJson=jsonO.getJSONArray("list");
                for(int i=0;i<hintsJson.length();++i) {

                    hintsList.add((String) hintsJson.get(i));

                }

            } catch (Exception e) {
                e.printStackTrace();
                response="nothingReceived";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            return;
        }

    }

    protected class json_signUp extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {

            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("\"name\"", "\""+s[0]+"\""));
                params.add(new BasicNameValuePair("\"email\"", "\""+s[1]+"\""));
                params.add(new BasicNameValuePair("\"mobile\"", "\""+s[2]+"\""));
                params.add(new BasicNameValuePair("\"action\"", "\""+s[3]+"\""));
                String sudo="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                sudo=sudo.replace('=',':');
                List<NameValuePair>actual=new ArrayList<>();
                actual.add(new BasicNameValuePair("paramsSent",sudo));

//                 getting JSON Object
//                 Note that create product url accepts POST method

                JSONObject jsonO =JSONParser.makeHttpRequest("http://www.facilities.aicte-india.org/dashboard/mobile/college/sms_register.php", "POST", actual);
                Log.d("key",actual.toString());
                Log.d("response",jsonO.toString());

                success=jsonO.getInt("success");
                registrationStatus=jsonO.getString("message");

            } catch (Exception e) {

                e.printStackTrace();
                str= "fatal error";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            return;
        }

    }

    protected class json_otpverify extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {

            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("\"email\"", "\""+s[0]+"\""));
                params.add(new BasicNameValuePair("\"otp\"", "\""+s[1]+"\""));
                params.add(new BasicNameValuePair("\"action\"", "\""+s[2]+"\""));
                String sudo="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                sudo=sudo.replace('=',':');
                List<NameValuePair>actual=new ArrayList<>();
                actual.add(new BasicNameValuePair("paramsSent",sudo));

//                 getting JSON Object
//                 Note that create product url accepts POST method

                JSONObject jsonO =JSONParser.makeHttpRequest("http://www.facilities.aicte-india.org/dashboard/mobile/college/sms_register.php", "POST", actual);
                Log.d("key",actual.toString());
                Log.d("response",jsonO.toString());

                success=jsonO.getInt("success");
                registrationStatus=jsonO.getString("message");

            } catch (Exception e) {

                e.printStackTrace();
                str= "fatal error";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            return;
        }

    }

    protected class json_smslogin extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {

            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("\"email\"", "\""+s[0]+"\""));
                params.add(new BasicNameValuePair("\"token\"", "\""+s[1]+"\""));
                String sudo="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                sudo=sudo.replace('=',':');
                List<NameValuePair>actual=new ArrayList<>();
                actual.add(new BasicNameValuePair("paramsSent",sudo));

//                 getting JSON Object
//                 Note that create product url accepts POST method

                JSONObject jsonO =JSONParser.makeHttpRequest("http://www.facilities.aicte-india.org/dashboard/mobile/college/sms_login.php", "POST", actual);
                Log.d("key",actual.toString());
                Log.d("response",jsonO.toString());

                success=jsonO.getInt("success");
                registrationStatus=jsonO.getString("message");

            } catch (Exception e) {

                e.printStackTrace();
                str= "fatal error";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            return;
        }

    }

    protected class json_otpresend extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {

            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("\"email\"", "\""+s[0]+"\""));
                params.add(new BasicNameValuePair("\"action\"", "\""+s[1]+"\""));
                String sudo="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                sudo=sudo.replace('=',':');
                List<NameValuePair>actual=new ArrayList<>();
                actual.add(new BasicNameValuePair("paramsSent",sudo));

//                 getting JSON Object
//                 Note that create product url accepts POST method

                JSONObject jsonO =JSONParser.makeHttpRequest("http://www.facilities.aicte-india.org/dashboard/mobile/college/sms_register.php", "POST", actual);
                Log.d("key",actual.toString());
                Log.d("response",jsonO.toString());

                success=jsonO.getInt("success");
                registrationStatus=jsonO.getString("message");

            } catch (Exception e) {

                e.printStackTrace();
                str= "fatal error";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            return;
        }

    }

    protected class json_logout extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {

            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("\"email\"", "\""+s[0]+"\""));
                params.add(new BasicNameValuePair("\"token\"", "\""+s[1]+"\""));
                String sudo="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                sudo=sudo.replace('=',':');
                List<NameValuePair>actual=new ArrayList<>();
                actual.add(new BasicNameValuePair("paramsSent",sudo));

//                 getting JSON Object
//                 Note that create product url accepts POST method

                JSONObject jsonO =JSONParser.makeHttpRequest("http://www.facilities.aicte-india.org/dashboard/mobile/college/logout.php", "POST", actual);
                Log.d("key",actual.toString());
                Log.d("response",jsonO.toString());

                success=jsonO.getInt("success");
                registrationStatus=jsonO.getString("message");

            } catch (Exception e) {

                e.printStackTrace();
                str= "fatal error";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            return;
        }

    }

    protected class json_notification extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {

            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("\"email\"", "\""+s[0]+"\""));
                params.add(new BasicNameValuePair("\"token\"", "\""+s[1]+"\""));
                String sudo="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                sudo=sudo.replace('=',':');
                List<NameValuePair>actual=new ArrayList<>();
                actual.add(new BasicNameValuePair("paramsSent",sudo));

//                 getting JSON Object
//                 Note that create product url accepts POST method

                JSONObject jsonO =JSONParser.makeHttpRequest("http://www.facilities.aicte-india.org/dashboard/mobile/college/add_token.php", "POST", actual);
                Log.d("noti","noti");
                Log.d("key",actual.toString());
                Log.d("jsonO",jsonO.toString());

            } catch (Exception e) {

                e.printStackTrace();
                str= "fatal error";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            return;
        }

    }

}