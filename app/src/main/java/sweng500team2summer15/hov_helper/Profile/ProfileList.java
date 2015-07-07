package sweng500team2summer15.hov_helper.Profile;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Mike on 6/7/2015.
 */
public class ProfileList {

    final List<Profile> ProfileList = new List<Profile>() {
        @Override
        public void add(int location, Profile object) {

        }

        @Override
        public boolean add(Profile object) {
            return false;
        }

        @Override
        public boolean addAll(int location, Collection<? extends Profile> collection) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends Profile> collection) {
            return false;
        }

        @Override
        public void clear() {
            ProfileList.clear();
        }

        @Override
        public boolean contains(Object object) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return false;
        }

        @Override
        public Profile get(int location) {
            return null;
        }

        @Override
        public int indexOf(Object object) {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @NonNull
        @Override
        public Iterator<Profile> iterator() {
            return null;
        }

        @Override
        public int lastIndexOf(Object object) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<Profile> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<Profile> listIterator(int location) {
            return null;
        }

        @Override
        public Profile remove(int location) {
            return null;
        }

        @Override
        public boolean remove(Object object) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return false;
        }

        @Override
        public Profile set(int location, Profile object) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @NonNull
        @Override
        public List<Profile> subList(int start, int end) {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(T[] array) {
            return null;
        }
    };
}
