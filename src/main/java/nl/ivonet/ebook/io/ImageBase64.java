/*
 * Copyright (c) 2013 by Ivo Woltring (http://ivonet.nl)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package nl.ivonet.ebook.io;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageBase64 {

    private String defaultImage = "/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0a\n"
                                  + "HBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIy\n"
                                  + "MjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCADhAOEDASIA\n"
                                  + "AhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA\n"
                                  + "AAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3\n"
                                  + "ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm\n"
                                  + "p6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA\n"
                                  + "AwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx\n"
                                  + "BhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK\n"
                                  + "U1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3\n"
                                  + "uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3+iii\n"
                                  + "gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKA\n"
                                  + "CiiigAooooAKKKKACiiigApD9KaWCJlq5DWviNo2lF4rdmv51/hhI2j6t0/LNXCnOo7RVzOdSNNX\n"
                                  + "k7HY5BqOSaOFN8sqxqP4mYAfrXiup/EbX799kEsdnF/dhXLfixzz9MVnxaF4n19/Ne0vbnd/y0uG\n"
                                  + "IH4FyB+VdscvaV6kkjilmCbtTi2ey3PizQLT/W6taZ9FlDH8hmqL/ELwwn/MS3f7sMh/9lrz2L4Z\n"
                                  + "6z5e+6ubC0T/AKaSk/yGP1p//CBWaf8AHx4r0yP8Qf5sKpYbCrebfp/wxLxOJ6QS9Tvl+Ifhh/8A\n"
                                  + "mJFf96CQf+y1bt/GPh26H7rWLT/to+z+eK81/wCEEsX/AOPfxbpkn4gfyc0N8NNUePfZXthdp/0z\n"
                                  + "lI/oR+tDw+Fe02vX/hgWJxP8qfp/w57DDcwXKb4Jo5F/vIwYfmKmrwOfwt4n0V/N+xXce3/lpatu\n"
                                  + "/VDkVZ07x/4h0x9klz9qRfvR3S5P58EH6mpeAur05JlLH2dqkWj3OiuE0b4naVfusN/G1jL/AHmb\n"
                                  + "cn5jkfiMe9dtDLHPCssUiyI3KsrAg+4I61xVKU6btJWOynVhUV4u5NRRRUGoUUUUAFFFFABRRRQA\n"
                                  + "UUUUAFFFFABRRRQAUUUUAJXPeI/F2neG4P37ebdMPkt4yNze59B7n8M1ieM/HiaRv0/TWWS/6SSd\n"
                                  + "Vh/xb27d/SuB0Tw3qniy8a5aRlt92Z7ybJ+uM9T+g74ruoYROPtKrtH8zgr4tqXs6SvL8hdY8T6z\n"
                                  + "4suVt/m8qRv3dnb5w31A5Y/X8hWrYfD/AOywrd+I72Owi/54xsGlPtnkD8N1dBFLp3huFrTw/bK0\n"
                                  + "u3El3J8zP9PUfp7Vz1/dw+d5t/exea3/AD2lG4/QE5P0FdftXa1P3Y/iYKhrzVPef4Gims6PouF0\n"
                                  + "HRo/N/5+rrLN9R3/AFH0rNvfEev3/wDrdSkiT+7DhB+nP602KKW4/wCPXTdRmX+9HYyY/MgL+tWf\n"
                                  + "7F1lvu+HdR/76gH6GXNRGVGLu2m/PU19nNqy0Xloc9JavK++Vmkf+8zEn8zTPsH+zW5cW9xZfPe6\n"
                                  + "Xf2iL96SS2JQe5ZNyge5Ip0cKSok0TLKjfMrKwIYeoI4NdEK8XszF0Gt0YP2D/ZpyWWz50+X/d4r\n"
                                  + "eNrsqG3UXpxYWl3er93zLWBmj/7+YCf+PUOtFbsFQvshLHXNcsP9RqU23+7I28fk2cfhWs3iOy1V\n"
                                  + "fK8QaLBcfw+fD8rj9c/kwqm2jayib/8AhHdS2/7PkH9BLn9KrvDcRfNPpeqQL/eksZCPzUMKwcqE\n"
                                  + "3fr9xqqc0rdPvLF14EsdVR7jwzqSyfxNa3DYYfQ9fzH41g2Oq6/4Ov2i/eWzL9+3mBMb++On4r+d\n"
                                  + "adrd2jzJ9lvoPtCt8qxygSKfpncK6db+21W2Wx8Q2y3EX8Nxtw6e/HP4j9ar2rStL3o/j/wTOVBX\n"
                                  + "vD3X+BseGfHGn+IQtux+zXv/ADyduH/3T3+nX+ddWDXhniPwdd6F/p1nI11pv3o7iPrHzxux05/i\n"
                                  + "HH0rp/BvxA81k03W5MN0hum4z6K59ffv39TzVsInH2lHVduxtRxbUvZ1tH3PTqKQEYpa889AKKKK\n"
                                  + "ACiiigAooooAKKKKACiiigBK4Px54z/siNtLsJP9OkX55F/5Yg/+zEdPTr6Vt+LvEUXhvRmuPla5\n"
                                  + "k+SCP+82Op9h1P4DvXk/hrQ7jxZrjNPJJ5Ct5t3cN15OcA+rc/QZPau7CUItOrU+FficGLryTVKn\n"
                                  + "8TLHhLwk+uyNe37NDp0J/eSc5lPcA/zPvjr07m8ufMgSxs4/s9jGu1Il46ev+FX7jyRDFaWsax2U\n"
                                  + "IAjjXgcd6zr1Hi0+7dPldYnZW/ukKSD+daTqupLml8l2HSoKlGy36sybxXhgX7PH5lxM4it4/wC/\n"
                                  + "IxwAfbuT2UMe1avw2j322tXE8cDXa6nJC00cW3cESNRjJJAO3OMnGTXF/A241HxPNqGta1qUt21k\n"
                                  + "witomwFRmBLPgADdjgegLetdj8K3d7LxRubdt8R3ir9AVwK46lXnOqEOU7+iiisTQTFcL4r0K303\n"
                                  + "drllGkKbx9viUYR1Y4Mu3oGUnJP8S5zkhcd3WV4jiS48MarC33ZLOVfzQinGTi7oTV1Y5PQPD9vr\n"
                                  + "d7cXF/H5lhaSmGK3b7ssq43u46MFPygHjKsSD8uO/A2jgVy3w5n+1+A9NvO9yJLhvq8jMf511dOc\n"
                                  + "nJ3YoqysFJiloqSjjviVsh8E3l75Mcstu8Ese9c8iZDgHqM9OPWudtI5v3tvdLH9rt32SeWpCMCA\n"
                                  + "VZQSSAylT1ODuGeK1/jE7RfCnXHRtrqsOGH/AF2jrnPjKt9o/hqy8S6RqElndR+XbXHl4xNGQSuQ\n"
                                  + "QRlTnHs7VrTq8hE4cx0mn3UthuTb5kEnDwt0IPB/GuT8X+DorSFtY0dd1i3+uh7wf/Y/y+nTZ8IT\n"
                                  + "3OpeENIvrqTzLia2DySNgZJJ544ro7ST7K/3d0TfK8fUEfSuyFVwfPH5ructSgqkeWXyfY5r4f8A\n"
                                  + "jTd5WialIS33LaZu/ohPr6fl6V6dx+deI+NvC/8AYV6t7YL/AMS24bcm3P7t+u3Pp3H0I7V33gLx\n"
                                  + "T/b+m/Zrpv8AT7dQHz/y0XoH/off61OKoxlH21PZ7meFrSjL2NTdbHZUUUVwHohRRRQAUUUUAFFF\n"
                                  + "FACU1mCpuanCuN+I2tf2X4ca3ifbPekwr7Lj5j+XH41dODqSUV1M6k1Tg5PoeceJ9YufFXib9wrN\n"
                                  + "Fu8m0j/vDOAfqx5/L0r1HSdIi0DQ4tNi2tL9+d1/jY9fw7fQCuL+GGjebez6zOv7q3/dQ+8hHJH0\n"
                                  + "Bx+PtXo7Lvfe9d+Kmk1RjsvzOLCU271ZbspeVVXU4v8AiU3v/XtJ/wCgmtby6qapH/xKb3/r2k/9\n"
                                  + "BNcvMdtjzH9m3/kX9c/6+o//AECuv+FH/Hn4p/7GO9/mlcj+zb/yL+uf9fUf/oFdd8KP+PPxT/2M\n"
                                  + "d7/NK5zQ9BooooAKz9d/5AGp/wDXpJ/6Ca0Kz9d/5AGp/wDXpJ/6CaAOd+FX/JLvD/8A16/+zGuy\n"
                                  + "rjfhV/yS7w//ANev/sxrsqACiiigDg/jN/ySbXf92H/0dHWL8dP+STf9vEH9a2vjN/ySbXf92H/0\n"
                                  + "dHWP8dP+SU/9vNv/AFoAufDyP/i3mgf9ea/zNdL5VYfw4T/i3Ph3/rzX+ZrqPLroUtDOxUlsrfUt\n"
                                  + "PuNMvF3QXC7foexHvnB+oryC3lvvBfiv5l/e2r7ZF6CSM9fwIwR6cele07K4z4m6N9qsLfWYl/e2\n"
                                  + "/wC6n/3SeD+BOP8AgXtXRhaiUuSW0jjxdJuPtI7xPQrO6ivbOG7gbdFMgdW9iMirFedfCzWftFjP\n"
                                  + "pMrfNb/vYv8AcY8j8D/6FXotcdam6U3E66NRVIKQtFFFZmoUUUUAFFFFACEZFeJfEfU/7Q8Uvbr8\n"
                                  + "0VmgiX/ePLH9QPwr2maRYoHlb7qqWJ9gM14NoMT+IPGtq8vzfaLozP8AQEuR+QxXoYCKTlUfRHnY\n"
                                  + "+TajTXVnrvh7SjpHh2zsdv71U3y/7x5b9SR+FaWyrDj56btrjcnJtvqd0YqKSXQi2VT1RP8AiS6h\n"
                                  + "/wBe0n/oBrR21U1Zf+JLqH/XtJ/6CaVyjyP9m3/kX9c/6+o//QK6/wCFH/Hn4p/7GO9/mlch+zb/\n"
                                  + "AMi/rn/X1H/6BXX/AAo/48/FP/Yx3v8ANKgZ6DRRRQAVn67/AMgDU/8Ar0k/9BNaFZ+u/wDIA1P/\n"
                                  + "AK9JP/QTQBzvwq/5Jd4f/wCvX/2Y12Vcb8Kv+SXeH/8Ar1/9mNdlQAUUUUAcH8Zv+STa7/uw/wDo\n"
                                  + "6OsX46f8km/7eIP61tfGb/kk2u/7sP8A6OjrF+On/JJv+3iD+tAG98Nk/wCLa+Hf+vNf5muq2VzP\n"
                                  + "wzH/ABbLw7/15r/Wur21aYiLZUV1ZQ6hp91ZS/6q4jKN+Ixn/PpVrbSgUXE1fRnhfhq8m8O+Mrfz\n"
                                  + "/l8uc20/0J2n8jg/hXvfavDviJYfYvF9w6/KlwizL9SMH9VJ/GvYNDvhqWhWV9/z2hV2+uOf1zXd\n"
                                  + "jvfjCquqPPwPuSnSfRmlRRRXnHpBRRRQAUUUUAYniy5+y+E9Ul/6d3UfUjA/U15n8LrbzfFLy/8A\n"
                                  + "PG2ZvxJAH6E133xCbZ4Jv/8Aa8tfzkWuP+FA23uqTP8AdjhTP4lj/wCy16NDTCTf9dDza+uKgj1X\n"
                                  + "FGKq2Oo2mp2y3FncRzxN/FG2fwPofY1brz2mtGeimnqhMVS1cf8AEl1D/r2k/wDQTV6qWr/8gXUP\n"
                                  + "+vaT/wBBNAHj/wCzb/yL+uf9fUf/AKBXX/Cj/jy8Vf8AYx3v81rzb4L65D4d8AeJ9UlZVS3lRv8A\n"
                                  + "eOwgD8Tj8M11fh34WaTe6F9t1+51aWe7c3F1b/amjiMr/MfkXHzcgH3HtSGel3fiHRtP/wCP3V7C\n"
                                  + "2/67XKJ/M1mN8Q/BiPsPijR8/wCzeIR+YOKybT4beAbK2ZovDds2373nAyMvud5P6Vr2/hLwvEi+\n"
                                  + "V4f0lopPu/6HH+R+WgDQsfEuh6rxpur2F6/923uUkP5KSafrT7vD+pf9esn/AKAa5698AeFbtJ3/\n"
                                  + "ALG062l2geZAnksnPXKYINcrrr654AsriZtVl1bwtNE1vL9sbdcWbOCFKyYy65IGG6cD3oA6z4U/\n"
                                  + "8kv8P/8AXoP/AEI1v6h4g0bSONR1awsvT7RcpH+hIryHwZJrnijwhpeh6ffvpOhWcHlXeowt+9uZ\n"
                                  + "MkmOI/wKARluvIHrXb2Xw48GaVMwXQbK5lyPmugZ5CcDkl8jP09aANMfETwZv2f8JTpOf+vpMfnn\n"
                                  + "FXbfxd4cu/8Aj117S5/+ud5Gf5GqbeFPDUv7l/D+iNt/5Z/Y4/8ACsm7+H3gy905lfw7YLF5v/LF\n"
                                  + "PL7dcoRQAz4xSJL8JtcZPmXZF83b/XR96yvjp/ySn/t5t/603Vvg3ot/bS2llf6pYedFjyo7otC3\n"
                                  + "cbkIOQCAevaua8d63cax8EXhvpI21XT76KzvVXtKhIz/AMCGDnpyaAPS/hiP+LZeHf8ArzX+tdZi\n"
                                  + "uU+GP/JMvDv/AF5r/WuspgJijFLUNxcw2kDTXE8cMS/eaRgAPxNAjzT4tW37/S7v+8rxN+BBH8zX\n"
                                  + "TfDi5E/gy1T/AJ4u8f8A48SP0IrD+J00N74c0q+t28yKSf8AdtzyGRiDz9KvfCp93hm5X+7dt+qo\n"
                                  + "a9CeuDV+j/zPOhpjHbqjvKKKK849IKKKKACiiigDlviGm7wRf/7Plt+Ui1x3wq+efWYf+ekKfzYf\n"
                                  + "1rvPGNsLrwhqsX/Tuz/98/N/SvOfhbceV4luIf8AntbH81IP8s16NB3wk15/5Hm19MVB+Rzuj3dx\n"
                                  + "YTLLazyQy/3o2x+B9R7GvRtJ8c3HypfwrJ/00j+U/iOh/SvOp4PsWrXVv/zxndPyYgVq2jV21qVO\n"
                                  + "pG7Rx0asqcrJnr1nrNje/wCqnXd/db5T+R6/hT9Y/wCQLqH/AF7Sf+gmvObZq37LULiL5Vnbb/db\n"
                                  + "kfka8yphrbM9OGJvuj5x8AazHZXqWOqW1zPpTTi6aCGLcZJVUhAc4GOf85r26L4iapLbPDb+CNYk\n"
                                  + "ZX3RsykB/wAQpxXdQas7JsljX/gPH6VZR7SX+8v+fauZwaOhTTPPf+Ew8bS20v2f4e3PnTYG2S52\n"
                                  + "gD1JKj370x9c+KGyKGy8H6arLlmaa6GFPp/rAe9emLb238Df+PVYSNE+7UlnjGva/wDFfR/Duo6n\n"
                                  + "qGn6LDFCqMy26mRgN2CSNx4Hc9utctZ/Fl/EXgfVNG8Q2X2mWZXRZLWNQRuU7SyZ5w2DleeOnHP0\n"
                                  + "e0SP95a841z4HeD9YuWuYIrnS5WYlvsEgVCT/ssCB9FwKAPMrP4oReEPhtpWgabp7f2uquZJZ8CO\n"
                                  + "MszNnGcseR1wOO+MV1OgeIvifrrNqNlbaTLYLKEElxH5e/AGSpBGQDxn1B9K2tK+Afg+wuFmuWv9\n"
                                  + "R2/8s7iUBPxCKpP0Jr0u3sba0gS3t4liijULHHGoCoB0AA4AoA8wi1f4rW+pv9o8KaXPbqzN50c4\n"
                                  + "HHOMZk/pULeNvHNvpjvcfDu5kbzy22GcnjHXhWr1n7MlIyQp9/bQB5cfiVqIvIob3wNrsX7gMzKr\n"
                                  + "Ht0+4K8v+Imvpq9tdXFjo2oaT9vaL7db3SY8x487H65BwSOg6d6+mpJrdP7zf7tUptR2f6qL/vpi\n"
                                  + "f0qlBslySMz4Y/8AJM/Dv/Xmv9a6G71O0sv9fOq/7PU/kOa5u71G4ZNnmbV/ux/KP0rAuWrop4a+\n"
                                  + "7OeeJtsjZ1Txw6BksLb/ALaTf0A/qa881rUrvUZvNvLmSZv4d3Rfoo4H4VoXTVg3hr06FGENkeZX\n"
                                  + "rSnuzr/GH7r4d+G4v73lv/5DP/xVbvwoTb4bu2/vXjfoiCsL4lj7Jp+gaWn/ACxiP/joVR/Wup+G\n"
                                  + "lt9n8HQP/wA9pZH/APHsf+y1yVf90v3f+Z00V/tVuy/RHY0UUV5p6gUUUUAFFFFAEFzClxaywv8A\n"
                                  + "dkQofoRg14T4WnfRPGtkkvy+Xcm3k/HKH9Tn8K98rw34gac2meL7iVPkS4xcRt7ng/juBP4ivQy9\n"
                                  + "p81N9Uedj01y1F0ZN43s/wCz/GV18u1LhVmX/gQwf1U1VtHrovGW3XfCekeJYl+ZVCT7f4d3B/Jx\n"
                                  + "j8a5O0krvovmpK+60fyOGquWo7bPVfM3kv7a3+SWdVf/AGmrQg1zTk+/fQf99VpfDz5tQ1X/AHIP\n"
                                  + "/Z619f8AGlloGpfYZ7aSR/JEu5XRRgkjHzMOflrjnU9/kUbv1OunD3OdysvQx7fX9Ld0T+0IN7Nt\n"
                                  + "X5u/arzeIdIt5nhl1CBXVtrLu6EdQaydV8Y2/iCwk0u0tWWedlVWaeEgYYE8BiegPQV03g0btDf/\n"
                                  + "AK/Ln/0a1Z1I8seaSa8rmlOTlLli162KyeKtD/6Clt/31/8AWrWsdQs75PNsrmCdP70Lhh9DjpWS\n"
                                  + "vjS3/tn+z2smX/SjaeYsqE7gcZ2Z3Y98U/xBaw2V7p+qWsax3TXUdvJt486NztIbHXH3ge2KwcVd\n"
                                  + "Jpq/nc2U3a6adt9LG7Jcx28LzSyrHEvLMzAAfUngVlf8JhofT+0Y2/2lVmX8wMfrVXTbOPX7mTU7\n"
                                  + "8edaxytHZW7cx4UlTIR0JJBxnoAMdasz+KLa3eYRWN7Pa27FJriGIGNCOuBkMQOc7QcYqeVXta7L\n"
                                  + "53a90kadlqVpqEPnWd3FcR/3o3DD6HHQ1OXrD1XTEuIf7a0fbHfqnmRyRcC5XGdj4+8COhPIOCKo\n"
                                  + "wzL4vu0hDMNKjhjmmjzjzmcblQ4/hUYJHckDpSUE1dbdQc2vde/TzL8/irRopHT+0oWZfvLHl9v1\n"
                                  + "KggU611nTtQ3pZXcEzr95Vb5l+oPI/KnXOr2ejzJp9nYTzvHGHaGzRQIlOQM5IAzg4HXike10zxV\n"
                                  + "pq3Cq6yrkRzKuyaBwcEZ6ggjkdD7iqtFK7TS7k3k3ZNN9v8AglfUNStNPRHurmOBGbavmNjJ9qzE\n"
                                  + "1nTr2bybW7gkf721W5461Hpl1NceJtKiutv2q3a6hm29CyheQOwYYb8aveL7a+udR0aLTY0affL8\n"
                                  + "zfdQFMFm9hnOO/A71srRkovtuZOTlFyXfbqY19qtjbzeTLcxK/8AdZuay31G0uH2RXMbP/dVq76O\n"
                                  + "LT/COiM0sh2Kd0krcvNIe+O7E8AfTsK4HU9QuNVvft158u3Igt+0Kn+bHufwHFb0J870Wncwrxcd\n"
                                  + "3r2M27equjWf9peJrC027kacM3+6vJ/RTS3cldB8PLVYp9R1u6+W3s4iqt7kbmP4AAfjXXUlyUmz\n"
                                  + "khHnqJGX8Sb/AO1+LJYk+5axLF+PLH/0LH4V6x4csf7N8OafZt96OBd3+8Rk/qTXiukQS+J/GUXm\n"
                                  + "r/x9XJml/wBlQdxH5DH5V78OlcONfJCFLt/X+Z3YL35zq9x1FFFecekFFFFABRRRQAlcL8TdGN/o\n"
                                  + "SX0S/vbMkt/1zbAP5EKfoDXdVFLFHPA8Mi7kkUqyt3BGCKulUdOakuhlVpqpBxfU8o+H17DqWn3/\n"
                                  + "AIYvf9VcIzw+394D3HBH0NctJbzabqFxYzrtlhco34dx7Hr+NWNVsbvwb4p/dNta3cSwSf30J4z6\n"
                                  + "8ZU/jXU+LbOLxFolr4p01Pm8vFzGvVQOCT7qcg+3PQV7fMoz5l8M/wA/+CeNZyhyv4o/l/wDR+Gj\n"
                                  + "brnVf+ucH/s9beu+KbTStV+wyadLcy+Usu5dgGCSAPmI/umuL8BeIdO0VtQbUbjyfPWPy/kZs7d2\n"
                                  + "egOOo611F34j8D6hN5t4Le5l27d8ti7HHpkp05NcNem/btuLa8jso1F7BJSSfmZuqa/HrSWttBpk\n"
                                  + "lvtuo5WkkePGFbJHysTmui8En/inm/6+rj/0a1czrl94NuNEvYdNs7X7Y0RWLy7BlO7thtgwffNX\n"
                                  + "fDPiXTNK0prS8mljnW5nZl8iRuGkYg5CkHIIPWlUg3StGL32HTmlVvKS23Lr+J9Jtb+eRtHnXyZ2\n"
                                  + "hkvFijxuBwTw24jJ9O9TeKLSWB7fW0md0sTua3fBXaeGZfRwCSCc9COM1TOqeCHvftf2VGumk3+Z\n"
                                  + "/Z8pbdnOfudc0mr60dft206ytrlbaVgJ7iZDGPLBBKqDyScYzgAAms1FqSai13uaOScWm0+1jX8H\n"
                                  + "lT4RsEH/ACzjKN/vKxB/UGsnRL22svDkqXUiq1rJOtyrMBgh2JyPcEH3zRaXs2hXtw6QyXOm3D+b\n"
                                  + "JHDzJDIerBe6twSByDk85qafWvBt1dLd3SWzXS4+aazbzOOnBXJI/Shxak9G03fQFJWWqTStqavh\n"
                                  + "uN7TwnpqT/K8dqm7dxt+UHB+g/lWB8Nvl029hb7/AJqS/wDAGjUr+GAR+FT6lrMmuwtY2Mc8NlJx\n"
                                  + "PeTRmPMfdY1IySRkbiAAD3NRSmbTb+LUNNjWTbEIZrXcF8yNckbSeAy5OM8EEihQbjJPdg5Lmi1t\n"
                                  + "EsLKlv4o1qGZlV5Gimj3cbo/LC5HqAVIq34RbzbbU7hP9VLfyNG3ZgFVSR6glTVW71/wrqSJ/a9u\n"
                                  + "m+PpHfWpynrgkEfkeaS58WW6Wn2bQbZpW27UkMTRwQ+hJIGcf3QOfak4zlG3K+npoNSjGV+ZdfUz\n"
                                  + "bGQSfEqSROY/tE4/FYYgf1BH4V2mo6laaTYyXd5J5cUf5sewA7kngCvP7Ke30TWdNuLqSTyo1n86\n"
                                  + "byyxaRwCSQoJyTmpvFGu6drVzpv2CVp/Jd3k3ROoX5cA/MAM5rSdFznGOtrbmcKqhCT0vfY6nWrC\n"
                                  + "HxP4fR7WRd7KtxaTejYyD9CCQfYmvL5bh3T51aOVWKyRt1RgcEH6Guq8L+KLTRbe40/UZGit1ffb\n"
                                  + "NsZhhskpwDjByR7H2rnvGF9o17qSX2kXXmNcfLPH5Tr8wHDjIA5HB+g963w0ZU5uDTt0ZjiZRnFT\n"
                                  + "TV+qOfuGeV0RFZnZtqqvVieABXZeKpF8K+C7Pw7Ey/arpd9yy+mcsfxOAPYGoPA+kwq8viTUv3dl\n"
                                  + "ZqTEW6Mw6t77eg9z7Vzd5cX3jTxT+6X97cPshXtHGOmfYDJP410SanUt9mOr9f8AgHOrwp3+1LRe\n"
                                  + "n/BOy+FWj7VutYlX7/7mL6AgsfzAH4GvTqo6Xp8Olabb2MA/dQoEX39SfcnJ/Gr1ePXq+1qOR7GH\n"
                                  + "peypqIUUUVkbBRRRQAUUUUAFFFFAHJeOfDP/AAkOk74FH223y8R/vjumffjHuB71514L8Sv4f1N7\n"
                                  + "S83fYLhtk6sv+rfpux+hHp9K9xOK8y+IHgwS+bremxnf1uYV/i9XA9fUd+vrnvwlaLXsamz2PPxd\n"
                                  + "GSftqe63MLxZ4afQLz7XZ/Npdw2UZeRGTyFJ9PQ+n65dtc1seEPFtvb2r6HreJtLmXYrNz5Wex/2\n"
                                  + "e+R90/pD4l8K3GgSfarVmuNLk+ZJupTPQMR+h6Gu+E3B+yqb9H3/AOCcEopr2lPbquw+C4rRhuP9\n"
                                  + "quXguq0YbmnOAQmdJHcf7VWkuK56K5q0lzXO4HQpm8s9PFz/ALVYq3NP+01m4Gima5npjT1m/aaj\n"
                                  + "a5o5A5zQa5/2qrS3FU3uaqSXNaKBm5luW4rPnuKgmuazp7qt4wMJTJLi4qfw9oFx4n1PyV3LaxsG\n"
                                  + "nm9B6D3Pb060aB4evvEl5si3R2qt++uGXgew9T7fnWv4l8S2Wj6cfDnhw7VXKz3CtyT/ABAN3Y9z\n"
                                  + "+A9ic3f2VL4vyCMVb2lT4fzKnjnxHFNs0HSdq6da4WTy+jsvQA9wP1PPauo+HPhf+y7P+1LyPbdX\n"
                                  + "Cfu1brGnXn0J4P0A9653wD4M/tKdNWv4/wDQo2zDG3/LYjuR/dB/P6dfXwMCuHFVY04+xp/NnZhq\n"
                                  + "MqkvbT+SHUUUV556QUUUUAFFFFABRRRQAUUUUAFIelLRQB5f40+H5bzdT0SP5j80tqvf1KD19vy9\n"
                                  + "K57wx41uNCX+z76P7Vp3KtC2C0YPUDPUdeDx9K9v7etcf4p8B2mvhrm222t//wA9MfLJ/vAd/cc/\n"
                                  + "Wu+hioyj7Otqu551bCyjL2lHR9jm9R8G2mr239p+FbmOSJuWtd2MH/ZJ6H/Zb8+1cczTWkz29xHJ\n"
                                  + "HKv3o5FIK/gamlt9e8F6nu/eWkv8Mi8xyD0z0Yex6egrqbfxto2vxJaeKdOjVvurcRqSB78fMv4E\n"
                                  + "iu+LnBXXvx/H/gnE+Sbs/dl+BzUV3VpLqtybwBb3sP2jw5rENxF/zzkYH8Ny/wBRXP3nh7X9N/4+\n"
                                  + "NNn2/wB6NfMH5rnH401VpT2evbYlwqQ3Xz3La3VSC7rnhebPv/L+lPF5VukSqhvm7qNrqsT7ZTDe\n"
                                  + "/wC1R7IHUNh7qq0l3SWejazqX/HrptzIjfxMu1fzOBXQ2vw9mih+0a7qkFnbr95VYE/QscAfrUSq\n"
                                  + "UobsqMKs9kck9w8roiKzO3yqqrksfQAda6zSPA+2H+0fEc62VkvzNCzYZv8Aeb+H6Dn6VNL4q8M+\n"
                                  + "FVaLw9ZrdXW3DXUmcfix5P0GBXKXF5r3jTU1T95dS/wwx8Rxj1x0A/2j+dS5VJrT3Y93v/wCrQg9\n"
                                  + "fel2Wxs+I/HPm2/9laDF9k05V8vzFXazjuAP4QfzPfFTeDPAL6nt1HVomjsuscLZBm9CfRf5/Tr0\n"
                                  + "Xhf4c22lsl3qhS6uh8yx9UjPrz94+549u9d6OK4quKjTj7Oj82dlLDSqS563yQ2KNIY1RFVVVdoV\n"
                                  + "RgADoAO1SUUV556QUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUlMeVIx8zqv+8QKqSaraRf8ALXd/\n"
                                  + "uqT/ACFNJvYVya6s7a+t2t7qCOaJvvJIoIP4GuC1n4WWtxul0i5+yt/zxky0f4HqP1rrJPEMCfdt\n"
                                  + "7lv+AgfzNVX8TS/waZI3+9KB/IGt6TrU/gMKtOlU+NHk154a8SeHZ/tH2a5j2/8ALxasWH5ryPxx\n"
                                  + "Vuw+IniKx+RrmO7Rf4bhAT+Ywfzr0aTxLqn8Glx/8ClP9FrI1Ge51L/j60LTJv8AakQsfz4Nd6ru\n"
                                  + "elWCZxPDcmtKbRkL8TkuE2aloFvP/uyA/owP86P+Ey8IS/63wqi/7sMX/wBaq1x4ZS4+7pdvD/1z\n"
                                  + "aX+RciqTeC5n+58v5/1rRU8M9rr5/wDBM39Z62fyNb/hMPBsX+q8Lhv96GL/ABNA+JdnaD/iW+Hb\n"
                                  + "eH/gQX9FX+tZC+C7n/ab/PtVu38Lpb/e02Gb/ro8v9HFDp4Zb3fz/wCCC+s9LL5L/Ir3/wASdfux\n"
                                  + "+6kgtU/6Yx8/m2f0rNh0nxJ4nm83yL27/wCm1wxCj6FuB9BXcad52m/Pa6Bpkb/3lQ5/MkmtpPEu\n"
                                  + "qYw+mw/8BlI/pWbrcn8KCLWGc/4s2c/o/wAKlR1l1i73/wDTG3yB+LHn8gPrXf6fpllpVsLexto4\n"
                                  + "Iv7sa4z7k9Sfc1kp4muf4tLb/gMoP8wKtR+IoW+9bXK/8BB/ka4qsq1T4ztpUqVP4UbdJVCPV7ST\n"
                                  + "/loy/wC8hH9KtpNFL9yRG/3WBrmaa3Oi6JaKKKQwooooAKKKKACiiigAooooAKKKKACoZVd/uNtq\n"
                                  + "aigDPNkj0fYU/u1fxRiq5mKxQ+wp/do+wp/dq/gUYFHOwsUPsKf3aPsKf3av4oxRzsLFD7Cn92j7\n"
                                  + "Cn92r+KMUc7CxQ+wp/do+wp/dq/ijFHOwsUPsKf3aPsKf3av4oxRzsLFD7Cn92j7Cn92r+BRgUc7\n"
                                  + "CxQ+wp/do+wp/dq/ijFHMwsQxI6/xbk/2qnooqRhRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQ\n"
                                  + "AUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAH/9k=\n";

    /**
     * Decode string to image.
     *
     * @param imageString The string to decode
     * @return decoded image
     */
    public BufferedImage decodeToImage(final String imageString) {

        final BufferedImage image;
        final byte[] imageByte;
        try {
            final BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            final ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    /**
     * Encode image to string.
     *
     * @param image The image to encode
     * @param type jpeg, bmp, ...
     * @return encoded string
     */
    public String encodeToString(final BufferedImage image, final String type) {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final String imageString;

        try {
            ImageIO.write(image, type, bos);
            final byte[] imageBytes = bos.toByteArray();

            final BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            return defaultImage;
        }
        return imageString;
    }

    /**
     * Encode image to string.
     *
     * @param image The path to encode
     * @param type jpeg, bmp, ...
     * @return encoded string
     */
    public String encodeToString(final String image, final String type) {
        try {
            final BufferedImage img = ImageIO.read(new File(image));
            return encodeToString(img, type);
        } catch (IOException e) {
            return defaultImage;
        }
    }

    public String encodeToString(final InputStream inputStream, final String type) {
        try {
            final BufferedImage img = ImageIO.read(inputStream);
            return encodeToString(img, type);
        } catch (IOException e) {
            return defaultImage;
        }
    }

    public String defaultImage() {
        return defaultImage;
    }

//	public static void main (final String[] args) throws IOException {
//		/* Test image to string and string to image start */
//		final BufferedImage img = ImageIO.read(new File("files/img/TestImage.png"));
//		final BufferedImage newImg;
//		final String imgstr;
//		imgstr = encodeToString(img, "png");
//		System.out.println(imgstr);
//		newImg = decodeToImage(imgstr);
//		ImageIO.write(newImg, "png", new File("files/img/CopyOfTestImage.png"));
//		/* Test image to string and string to image finish */
//	}
}
