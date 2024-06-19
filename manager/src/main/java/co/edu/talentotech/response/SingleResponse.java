package co.edu.talentotech.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class SingleResponse extends Response implements Serializable {

    private static final long serialVersionUID = 3L;

    private Records<?> data;
    private ResponseDetails responseDetails = new ResponseDetails();

}
