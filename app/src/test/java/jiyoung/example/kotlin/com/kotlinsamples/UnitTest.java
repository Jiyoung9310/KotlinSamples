package jiyoung.example.kotlin.com.kotlinsamples;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import kotlin.jvm.internal.Intrinsics;

/**
 * Created by user on 2018-05-28.
 */

public class UnitTest {
	public final void setInfo(@NotNull String a, @Nullable String b) {
		Intrinsics.checkParameterIsNotNull(a, "a");
		System.out.println(a + b);
	}

	@Test
	public final void test() {
		this.setInfo((String)"A", (String)null);
	}
}
