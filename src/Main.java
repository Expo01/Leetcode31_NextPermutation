import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
// editorial single pass approach, still beats 100% and 10% so not much better on space. maybe not worth being this fancy
public class Solution {
    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i + 1] <= nums[i]) {
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1);
    }

    private void reverse(int[] nums, int start) {
        int i = start, j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}


// editorial solution from video beats 100% and 5%
class Solution {
    public void nextPermutation(int[] nums) {       // [4,2,0,1,3,2,0]
        int n = nums.length, pivot = n - 1; // len 7 pviot starts at index 6

        while (pivot >= 1 && nums[pivot] <= nums[pivot - 1]) { // pivot must be >= 1 because can't swap 0 index with itself
            // decrement pivot while the number to R is smaller than num to L since need to find a viable swap combo to inc val
            pivot--;
            // note that this means all prior content is ordered in descending fashion
            // when this loop breaks, pivot will be val of 3 and pivot-1 will be 2 [4,2,0,1*,3*,2,0]
            // 3,2,0 is all descending
        }

        // note however that finding the num appropriate for swap does not mean pivot will be the correct num to swap with
        //[4,2,0,1*,3*,2,0] if we swapped a 3 and 1 it would be greater val than if we swapped 2 and 1 so now
        //need second traversal to find the correct num to swap 1 with
        if (pivot != 0) {  // pivot at this point is the last index before the viable swap index
            int i = n - 1;
            while (nums[i] <= nums[pivot - 1]) { // find the first num greater than the val at idex to be swapped.
                // since we know the preceding content is sorted, we can simply decrement and exit
                i--;
            }
            swap(nums, pivot - 1, i); // swaps the desired index with first val that is greater than it,
            // but least of all greater than numbers
        }

        //[4,2,0,2,3,1,0]  see that even after the swap, the right subarray from pivot to n-1 is still sorted
        int left = pivot, right = n - 1;

        while (left < right) { // because the subarray is sorted, just swap items working out to in
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}


// passes but only 20% beat. i think this is using extra space because of storeed variables?
class Solution {
    public void nextPermutation(int[] nums) {
        int storeJ = -1;
        int storeI = -1;
        for (int i = nums.length - 1; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if (nums[j] < nums[i]) {
                    if (j > storeJ) {
                        storeJ = j;
                        storeI = i;
                    }
                }
            }
        }
        if (storeJ > -1) {
            int temp = nums[storeI];
            nums[storeI] = nums[storeJ];
            nums[storeJ] = temp;
            Arrays.sort(nums, storeJ + 1, nums.length);
            return;
        }

        Arrays.sort(nums);
    }
}

// will want to work R to L swapping. have nested for loop starting at
// R, then comparng to all L digits, looking for value less than cur. if
// no values found in thee inner loop, decreeement outerr loop indeex
// to effectively go to L and then again work R to L. if swap occurs
// then void return. else for loops completed, just return the sorted array