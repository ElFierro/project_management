package co.edu.talentotech.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class MultipleResponse extends Response implements java.io.Serializable{
    private static final long serialVersionUID = 2L;

    private List <? extends Records> data;
    private ResponseDetails responseDetails = new ResponseDetails();
}

