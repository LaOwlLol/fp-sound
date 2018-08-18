/*
 * fp-sound version 1.0 - A 'bare bones' sound API for Java
 *     Copyright (C) 2018  Nate Gillard https://github.com/LaOwlLol
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package setup;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LineServiceTest {
    @Test public void testSystemSourceDataLine() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        LineService.SystemSourceDataLine(FormatService.PREFERRED_FORMAT).ifPresent(line -> {
                  System.out.println(LineService.LineDetails(line));
                  assert(!outContent.toString().isEmpty());
                  assert(errContent.toString().isEmpty());
              }
        );

        System.setOut(System.out);
        System.setErr(System.err);
    }
}
