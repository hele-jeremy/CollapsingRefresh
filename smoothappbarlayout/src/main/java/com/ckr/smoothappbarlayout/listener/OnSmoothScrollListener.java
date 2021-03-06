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

package com.ckr.smoothappbarlayout.listener;

import android.view.View;

import com.ckr.smartrefresh.listener.OnOffsetListener;

/**
 * Created by PC大佬 on 2018/2/9.
 */
public interface OnSmoothScrollListener extends OnOffsetListener {

	void setScrollTarget(View target);

	void setCurrentScrollY(int scrollY);

	void onScrollChanged(View view, int x, int y, int dx, int dy, boolean accuracy);

	void onScrollValueChanged(View view, int scrollY, boolean onStartNestedFling);

	void onFlingFinished(View view, float velocityY);

	void onDispatchFling(View view, int mScrollState);

	void setOnFlingListener(OnFlingListener onFlingListener);
}
