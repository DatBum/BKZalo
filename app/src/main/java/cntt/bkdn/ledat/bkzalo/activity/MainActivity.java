package cntt.bkdn.ledat.bkzalo.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cntt.bkdn.ledat.bkzalo.fragment.ContactFragment;
import cntt.bkdn.ledat.bkzalo.R;
import cntt.bkdn.ledat.bkzalo.fragment.PersonalFragment;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time= new SimpleDateFormat("hh:mm:ss").format(new Date(System.currentTimeMillis()));
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        settupViewpager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void settupViewpager(ViewPager viewPager) {
          ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ContactFragment(), "Contact");
        viewPagerAdapter.addFragment(new PersonalFragment(), "Personal");
       viewPager.setAdapter(viewPagerAdapter);
    }

     class ViewPagerAdapter extends FragmentPagerAdapter {
        public List<Fragment> fragments = new ArrayList<>();
        public List<String> titles= new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            if(fragments.isEmpty()||fragments==null){
                return 0;
            }
            return fragments.size();
        }
         public void addFragment(Fragment fragment,String title){
             fragments.add(fragment);
             titles.add(title);
         }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }


    }

}


