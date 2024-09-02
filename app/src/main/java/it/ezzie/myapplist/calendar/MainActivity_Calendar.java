package it.ezzie.myapplist.calendar;

import static android.view.View.GONE;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.time.DayOfWeek;
import java.time.LocalDate;
import it.ezzie.myapplist.R;
import it.ezzie.myapplist.databinding.ActivityMainCalendarBinding;
import mmcalendar.MyanmarDate;

public class MainActivity_Calendar extends AppCompatActivity {

    private ActivityMainCalendarBinding binding;
    private LocalDate currentDate;
    private View prevSelectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainCalendarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        currentDate = LocalDate.now();//Current Date
        initUI();
    }

    private void initUI() {

        LocalDate firstDateOfMonth = currentDate.minusDays(LocalDate.now().getDayOfMonth() - 1);// FirstDay of Month

        LocalDate firstDayOfPreviousMonth = firstDateOfMonth.minusMonths(1);//Last Day Of Previous Month

        LocalDate firstDayOfNextMonth = firstDateOfMonth.plusMonths(1); // First Day of Next Month

        int firstDate = firstDateOfMonth.getDayOfWeek().getValue();

        int dayInCurrentMonth =  firstDateOfMonth.lengthOfMonth();

        currentDetail();
        for (int i =0 ; i < 35; i++) {
            Button dateBtn = (Button) binding.glDays.getChildAt(i);
            if (i < firstDate) {
//                LocalDate dateOfLastMonth = firstDayOfPreviousMonth.minusDays(firstDate - i -1);
//                dateBtn.setText(String.valueOf(dateOfLastMonth.getDayOfMonth()));
//                dateBtn.setTypeface(Typeface.create("@font/roboto_regular", Typeface.NORMAL));
//                dateBtn.setTextColor(Color.parseColor("#4a4a4a"));
                dateBtn.setVisibility(GONE);

            } else if (i < dayInCurrentMonth + firstDate) {
                int index = i - firstDate;
                LocalDate date = firstDateOfMonth.plusDays(index);
                dateBtn.setText(String.valueOf(date.getDayOfMonth()));
                dateBtn.setTag(date);

                if(date.equals(LocalDate.now())){
                    View view = new View(this);
                    view.setTag(date);
                    if(date.getDayOfMonth() == LocalDate.now().getDayOfMonth()){
                       dateBtn.setBackgroundResource(R.drawable.red_dash);
                    }
                    onNumberClicked(view);
                }
                if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    dateBtn.setTextColor(Color.parseColor("#C80018"));
                }

            } else {
//                int nextMonthIndex = i - (firstDateOfMonth.lengthOfMonth() + firstDate); // Adjusted index calculation
//                if (nextMonthIndex < 7) { // Limit to the first 7 days of the next month
//                    LocalDate nextDate = firstDayOfNextMonth.plusDays(nextMonthIndex);
//                    dateBtn.setText(String.valueOf(nextDate.getDayOfMonth()));
//                    dateBtn.setTypeface(Typeface.create("@font/roboto_regular", Typeface.NORMAL));
//                    dateBtn.setTextColor(Color.parseColor("#4a4a4a"));
//                } else {
//                    dateBtn.setText(""); // Clear the button if it's beyond the first 7 days of the next month
//                }
                dateBtn.setVisibility(GONE);
            }

        }
    }


    public void previousMonth(View view) {
        currentDate = currentDate.minusMonths(1);
        monthSwitch();
        yearSwitch();
        initUI();
    }

    public void nextMonth(View view) {
        currentDate = currentDate.plusMonths(1);
        monthSwitch();
        yearSwitch();
        initUI();
    }

    public void onNumberClicked(View view ){
        LocalDate date = (LocalDate) view.getTag();
        if (prevSelectedDate != null) {
            LocalDate prevDate = (LocalDate) prevSelectedDate.getTag();
            if (prevDate.isEqual(LocalDate.now())) {
                prevSelectedDate.setBackgroundResource(R.drawable.red_dash);
            } else {
                prevSelectedDate.setBackgroundResource(R.drawable.bg_empty);
            }
            prevSelectedDate = null;
        }
        view.setBackgroundResource(R.drawable.bg_selected);
        prevSelectedDate = view;
        MyanmarDate myanmarDate = MyanmarDate.of(date);
        binding.MMYear.setText(myanmarDate.format("S s k"));
        binding.MMYear2.setText(myanmarDate.format(" M p f r "));
        binding.MMYear3.setText(myanmarDate.format("En"));

    }

    public void monthSwitch(){
        int monthInt = Integer.parseInt(String.valueOf(currentDate.getMonth().getValue()));
        String monthName;

        switch (monthInt) {
            case 1:
                monthName = "January";
                break;
            case 2:
                monthName = "February";
                break;
            case 3:
                monthName = "March";
                break;
            case 4:
                monthName = "April";
                break;
            case 5:
                monthName = "May";
                break;
            case 6:
                monthName = "June";
                break;
            case 7:
                monthName = "July";
                break;
            case 8:
                monthName = "August";
                break;
            case 9:
                monthName = "September";
                break;
            case 10:
                monthName = "October";
                break;
            case 11:
                monthName = "November";
                break;
            case 12:
                monthName = "December";
                break;
            default:
                monthName = "Invalid month"; // Handle invalid input
                break;
        }


        binding.tvMonth.setText(monthName);

    }

    public void yearSwitch(){
        String year = String.valueOf(currentDate.getYear());
        binding.tvYear.setText(year);
    }

    public void currentDetail(){
        monthSwitch();
        String year = String.valueOf(currentDate.getYear());
        binding.tvYear.setText(year);
    }

}