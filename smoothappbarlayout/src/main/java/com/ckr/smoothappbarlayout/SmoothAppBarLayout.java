/*
 * Copyright 2016 "Henry Tao <hi@henrytao.me>"
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ckr.smoothappbarlayout;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.ckr.smoothappbarlayout.base.LogUtil;
import com.ckr.smoothappbarlayout.base.OnFlingListener;
import com.ckr.smoothappbarlayout.base.OnSmoothScrollListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.ckr.smoothappbarlayout.base.LogUtil.Logd;


/**
 * Created by PC大佬 on 2018/2/9.
 */
@CoordinatorLayout.DefaultBehavior(SmoothAppBarLayout.SmoothBehavior.class)
public class SmoothAppBarLayout extends AppBarLayout implements OnSmoothScrollListener {
	protected final List<WeakReference<OnOffsetChangedListener>> mOffsetChangedListeners = new ArrayList<>();
	private SmoothBehavior smoothBehavior;

	public SmoothAppBarLayout(Context context) {
		super(context);
	}

	public SmoothAppBarLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	@Override
	public void addOnOffsetChangedListener(OnOffsetChangedListener listener) {
		super.addOnOffsetChangedListener(listener);
		int i = 0;
		for (int z = this.mOffsetChangedListeners.size(); i < z; ++i) {
			WeakReference ref = (WeakReference) this.mOffsetChangedListeners.get(i);
			if (ref != null && ref.get() == listener) {
				return;
			}
		}
		this.mOffsetChangedListeners.add(new WeakReference(listener));
	}

	@Override
	public void removeOnOffsetChangedListener(OnOffsetChangedListener listener) {
		super.removeOnOffsetChangedListener(listener);
		Iterator i = mOffsetChangedListeners.iterator();
		while (true) {
			OnOffsetChangedListener item;
			do {
				if (!i.hasNext()) {
					return;
				}
				WeakReference ref = (WeakReference) i.next();
				item = (OnOffsetChangedListener) ref.get();
			} while (item != listener && item != null);
			i.remove();
		}
	}

	@Override
	public void onScrolled(View view,int dx, int dy) {
		if (smoothBehavior == null) {
			initBehavior();
		}
		smoothBehavior.onScrolled(view,dx, dy);
	}

	@Override
	public int getCurrentOffset() {
		if (smoothBehavior == null) {
			initBehavior();
		}
		return smoothBehavior.getCurrentOffset();
	}

	@Override
	public void onScrollValueChanged(int scrollY,boolean onStartNestedFling) {
		if (smoothBehavior == null) {
			initBehavior();
		}
		smoothBehavior.onScrollValueChanged(scrollY,onStartNestedFling);
	}

	@Override
	public void onFling(float velocityY) {
		if (smoothBehavior == null) {
			initBehavior();
		}
		smoothBehavior.onFling(velocityY);
	}

	@Override
	public void setFlingListener(OnFlingListener callBack) {
		if (smoothBehavior == null) {
			initBehavior();
		}
		smoothBehavior.setFlingListener(callBack);
	}

	private void initBehavior() {
		CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) getLayoutParams();
		this.smoothBehavior = (SmoothBehavior) params.getBehavior();
	}

	public static class SmoothBehavior extends BaseBehavior {
		private static final String TAG = "SmoothBehavior";

		@Override
		public void onScrolled(View view,int dx, int dy) {
			if (view == mScrollTarget) {
				Logd(TAG, "onScrolled: dy:" + dy + ",mCurrentOffset:" + mCurrentOffset);
				int translationOffset = Math.max(-423, -dy);
				LogUtil.Loge(TAG, "onScrolled: translationOffset:" + translationOffset);
				syncOffset(translationOffset, dy);
			}
		}

		@Override
		public int getCurrentOffset() {
			return mCurrentOffset;
		}

		@Override
		public void onScrollValueChanged(int scrollY,boolean onStartNestedFling) {
			mTotalScrollY = scrollY;
			if (onStartNestedFling) {
				flagScrollY=scrollY;
			}
		}

		@Override
		public void onFling(float velocityY) {
			/*if ((mCurrentOffset==0*//*&&velocityY>0)||(mCurrentOffset==-423&&velocityY<0*//*)) {
				return;
			}*/
			fling(mAppBarLayout, mScrollTarget,Math.abs(mCurrentOffset),0,0,velocityY,false);
		}

		@Override
		public void setFlingListener(OnFlingListener callBack) {
			this.callBack=callBack;
		}
	}

}